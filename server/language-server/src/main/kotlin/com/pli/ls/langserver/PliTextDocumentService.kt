package com.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.TextDocumentService
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

import kotlin.system.measureTimeMillis
import com.pli.compiler.parser.CompilationUnit
import com.pli.compiler.parser.ast.*
import java.io.Closeable
import java.io.File
import java.net.URI
import com.pli.compiler.parser.PLIKParser
import com.strumenta.kolasu.validation.Issue
import com.strumenta.kolasu.validation.IssueType
import org.slf4j.LoggerFactory
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageClientAware

data class ASTWrapper(val ast: CompilationUnit,val errors: List<Issue>)
class PliTextDocumentService(val server : PliLanguageServer) : TextDocumentService, LanguageClientAware, Closeable {
  private val LOG = LoggerFactory.getLogger(javaClass)
  private lateinit var client: LanguageClient
  private val async = AsyncExecutor()
  private val syntaxTrees : HashMap<String, ASTWrapper> = hashMapOf()
  private val sources : MutableList<String> = mutableListOf()

    override fun completion(completionParams: CompletionParams?): CompletableFuture<Either<List<CompletionItem>, CompletionList>> {
        return CompletableFuture.supplyAsync {
            val completionItems: MutableList<CompletionItem> = ArrayList()
            try {
                val completionItem = CompletionItem()
                completionItem.insertText = "sayHello() {\n    print(\"hello\")\n}"
                completionItem.label = "sayHello()"
                completionItem.kind = CompletionItemKind.Snippet
                completionItem.detail = "sayHello()\n this will say hello to the people"
                completionItems.add(completionItem)
            } catch (e: Exception) {
                // Handle the exception
            }
            Either.forLeft(completionItems)
        }
    }

    override fun resolveCompletionItem(completionItem: CompletionItem?): CompletableFuture<CompletionItem> {
        return CompletableFuture.completedFuture(completionItem)
    }

    override fun hover(textDocumentPositionParams: TextDocumentPositionParams?): CompletableFuture<Hover> {
        return CompletableFuture.completedFuture(null)
    }

    override fun signatureHelp(textDocumentPositionParams: TextDocumentPositionParams?): CompletableFuture<SignatureHelp> {
        return CompletableFuture.completedFuture(null)
    }

    override fun definition(textDocumentPositionParams: TextDocumentPositionParams?): CompletableFuture<List<Location>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun references(referenceParams: ReferenceParams?): CompletableFuture<List<Location>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun documentHighlight(textDocumentPositionParams: TextDocumentPositionParams?): CompletableFuture<List<DocumentHighlight>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun documentSymbol(documentSymbolParams: DocumentSymbolParams?): CompletableFuture<List<SymbolInformation>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun codeAction(codeActionParams: CodeActionParams?): CompletableFuture<List<Command>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun codeLens(codeLensParams: CodeLensParams?): CompletableFuture<List<CodeLens>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun resolveCodeLens(codeLens: CodeLens?): CompletableFuture<CodeLens> {
        return CompletableFuture.completedFuture(codeLens)
    }

    override fun formatting(documentFormattingParams: DocumentFormattingParams?): CompletableFuture<List<TextEdit>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun rangeFormatting(documentRangeFormattingParams: DocumentRangeFormattingParams?): CompletableFuture<List<TextEdit>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun onTypeFormatting(documentOnTypeFormattingParams: DocumentOnTypeFormattingParams?): CompletableFuture<List<TextEdit>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun rename(renameParams: RenameParams?): CompletableFuture<WorkspaceEdit> {
        return CompletableFuture.completedFuture(null)
    }

  fun publishDiagnostics(uri: String, astWrapper: ASTWrapper) {
      val errors : MutableList<Diagnostic> = mutableListOf()
      //this.client.publishDiagnostics(PublishDiagnosticsParams(uri,errors))

      astWrapper.errors.map {
          //LOG.info("error ${it.position} ${it.message}")
          if(it.position != null) {
              val end_line = it.position!!.end.line - 1
              val end_column = it.position!!.end.column
              val start_line = it.position!!.start.line - 1
              val start_column = it.position!!.start.column

              val range = Range(Position(start_line,start_column),Position(end_line,end_column))
              errors.add(Diagnostic(range,it.message))
          } else {
              val range = Range(Position(0,0),Position(1,1))
              errors.add(Diagnostic(range,"Internal error: ${it.message}"))
          }
      }
      this.client.publishDiagnostics(PublishDiagnosticsParams(uri,errors))
  }
  fun parse(uri: String) = async.compute {
      val sourceFile = URI(uri).path
      val parser = PLIKParser()
      LOG.info("parsing: $uri $sourceFile")
      try {
          val errors: MutableList<Diagnostic> = mutableListOf()

          this.client.publishDiagnostics(PublishDiagnosticsParams(uri,errors))

          val elapsed = measureTimeMillis {
              val result = parser.parse(File(sourceFile), considerPosition = false)

              if (result.correct) {
                  LOG.warn("syntax tree added: $uri")
              }

              result.issues.map {
                  LOG.info("error ${it.position} ${it.message}")
                  val end_line = it.position!!.end.line - 1
                  val end_column = it.position!!.end.column
                  val start_line = it.position!!.start.line - 1
                  val start_column = it.position!!.start.column

                  val range = Range(Position(start_line, start_column), Position(end_line, end_column))
                  errors.add(Diagnostic(range, it.message))
              }
              syntaxTrees[uri] = ASTWrapper(result.root!!, result.issues)
              this.client.publishDiagnostics(PublishDiagnosticsParams(uri,errors))
          }
          LOG.debug("parsing: $elapsed msec")



      } catch (e: Exception) {
          LOG.error("e ${uri},${syntaxTrees[uri]}" + e.stackTraceToString())
      }
  }

//override fun completion(p0: CompletionParams): CompletableFuture<(Either<(MutableList<(CompletionItem..CompletionItem?)>..List<(CompletionItem..CompletionItem?)>?), (CompletionList..CompletionList?)>..Either<(MutableList<(CompletionItem..CompletionItem?)>..List<(CompletionItem..CompletionItem?)>?), (CompletionList..CompletionList?)>?)> { }
//
//override fun resolveCompletionItem(p0: CompletionItem): CompletableFuture<(CompletionItem..CompletionItem?)> { }
//
//override fun hover(p0: TextDocumentPositionParams): CompletableFuture<(Hover..Hover?)> { }
//
//override fun signatureHelp(p0: TextDocumentPositionParams): CompletableFuture<(SignatureHelp..SignatureHelp?)> { }
//
//override fun definition(p0: TextDocumentPositionParams): CompletableFuture<(MutableList<out (Location..Location?)>..List<(Location..Location?)>?)> { }
//
//override fun references(p0: ReferenceParams): CompletableFuture<(MutableList<out (Location..Location?)>..List<(Location..Location?)>?)> { }
//
//override fun documentHighlight(p0: TextDocumentPositionParams): CompletableFuture<(MutableList<out (DocumentHighlight..DocumentHighlight?)>..List<(DocumentHighlight..DocumentHighlight?)>?)> { }
//
//override fun documentSymbol(p0: DocumentSymbolParams): CompletableFuture<(MutableList<out (SymbolInformation..SymbolInformation?)>..List<(SymbolInformation..SymbolInformation?)>?)> { }
//
//override fun codeAction(p0: CodeActionParams): CompletableFuture<(MutableList<out (Command..Command?)>..List<(Command..Command?)>?)> { }
//
//override fun codeLens(p0: CodeLensParams): CompletableFuture<(MutableList<out (CodeLens..CodeLens?)>..List<(CodeLens..CodeLens?)>?)> { }
//
//override fun resolveCodeLens(p0: CodeLens): CompletableFuture<(CodeLens..CodeLens?)> { }
//
//override fun formatting(p0: DocumentFormattingParams): CompletableFuture<(MutableList<out (TextEdit..TextEdit?)>..List<(TextEdit..TextEdit?)>?)> { }
//
//override fun rangeFormatting(p0: DocumentRangeFormattingParams): CompletableFuture<(MutableList<out (TextEdit..TextEdit?)>..List<(TextEdit..TextEdit?)>?)> { }
//
//override fun onTypeFormatting(p0: DocumentOnTypeFormattingParams): CompletableFuture<(MutableList<out (TextEdit..TextEdit?)>..List<(TextEdit..TextEdit?)>?)> { }
//
//override fun rename(p0: RenameParams): CompletableFuture<(WorkspaceEdit..WorkspaceEdit?)> { }

  override fun didOpen(params: DidOpenTextDocumentParams) {
    LOG.info("didOpen: ${params.textDocument.uri}")

    val ast = syntaxTrees[params.textDocument.uri]
    if(ast != null) {
        LOG.info("FOUND ast: ${params.textDocument.uri}")
        publishDiagnostics(params.textDocument.uri,ast)
    }

    if(params.textDocument.languageId.contentEquals("pli")) {
      parse(params.textDocument.uri)
      sources.add(params.textDocument.uri)
    }

  }

  override fun didChange(params: DidChangeTextDocumentParams) {
    LOG.info("didChange")
    parse(params.textDocument.uri)
  }

  override fun didClose(params: DidCloseTextDocumentParams) {
    LOG.info("didClose")
  }

  override fun didSave(params: DidSaveTextDocumentParams) {
    LOG.info("didSave")
    parse(params.textDocument.uri)
  }

  override fun connect(client: LanguageClient) {
    LOG.info("connect")
    this.client = client
   }

  override fun close() {
    LOG.info("close")
  }
}
