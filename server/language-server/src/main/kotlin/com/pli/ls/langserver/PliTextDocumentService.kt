package com.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.Position
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
import org.slf4j.Logger
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageClientAware

import com.strumenta.kolasu.model.*

data class ASTWrapper(val ast: CompilationUnit,val errors: List<Issue>)
class PliTextDocumentService(val server : PliLanguageServer) : TextDocumentService, LanguageClientAware, Closeable {
  private val LOG = LoggerFactory.getLogger(javaClass)
  private lateinit var client: LanguageClient
  private val async = AsyncExecutor()
  private val syntaxTrees : HashMap<String, ASTWrapper> = hashMapOf()
  private val sources : MutableList<String> = mutableListOf()

  override fun connect(client: LanguageClient) {
    LOG.info("connect to TextDocumentService")
    this.client = client
   }

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

  override fun close() {
    LOG.info("close")
  }

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

    override fun definition(params: TextDocumentPositionParams?): CompletableFuture<List<Location>> {
      val locations: MutableList<Location> = mutableListOf()

      val line = params!!.position.line + 1
      val column = params.position.character
      val point = Point(line, column)
      LOG.info("definition $line,$column ")

      val astWrapper = syntaxTrees[params.textDocument.uri]
      if (astWrapper != null) {
          LOG.info("FOUND ast: ${params.textDocument.uri}")

          val target = astWrapper.ast.walk().filter { it.position != null && it.position!!.contains(point) }.lastOrNull()
          if (target != null) {
              LOG.debug("${target!!.javaClass}")
              when {
                  target is ProcedureDeclaration -> {
                      locations.addAll(resolveProcedureDeclaration1(target.label))
                  }
                  target is CallStatement -> {
                      locations.addAll(resolveProcedureDeclaration1(target.target))
                  }
                  target is DeclareStatement -> {
                      LOG.info("${target.javaClass.name}")
                      target.declarations.forEach { decl ->
                          if (decl.dataType is EntryType) {
                              locations.addAll(resolveProcedureDeclaration1(decl.name))
                          }
                      }
                  }
                  target is DataDeclaration -> {
                      LOG.info("${target.javaClass.name}: ${target.name}")
                      if (target.dataType is EntryType) {
                          locations.addAll(resolveProcedureDeclaration1(target.name))
                      }
                  }
                  target is Identifier -> {
                      LOG.info("${target.javaClass.name}: ${target.name}")
                      var x = target
                      val full = mutableListOf<String>()
                      while (x!!.parent != null && x is Identifier) {
                          full.add(0, x.name)
                          x = x.parent
                      }
                      val name = full.joinToString(".")
                      LOG.info("${target.javaClass.name}: ${name}")

                      locations.addAll(resolveIdentifier(astWrapper.ast, name, params.textDocument.uri, target))
                  }
                  else -> {
                      LOG.warn("Definition unsupported: ${target.javaClass.name}")
                  }
              }
          } else {
              LOG.warn("Definition target null")
          }
      }
      return CompletableFuture.completedFuture(locations)
  }


    private fun resolveProcedureDeclaration1(target: String?): List<Location> {
      val locations : MutableList<Location> = mutableListOf()
//        if(Index.procedureIdx.containsKey(target!!.toUpperCase())) {
//            LOG.debug("index hit $target")
//            val xx = Index.procedureIdx[target!!.toUpperCase()]
//            val rng = createRange(xx!!.node.position!!)
//            locations.add(Location(xx.uri,rng))
//            return locations
//        }
      syntaxTrees.forEach { entry ->
          val ast = entry.value.ast
          val uri = entry.key
          ast.walk().forEach { stmt ->
              when {
                  stmt is ProcedureDeclaration -> {
                      if(target.equals(stmt.label,true)) {
                          val rng = createRange(stmt.position!!)
                          locations.add(Location(uri,rng))
                          return locations
                      }
                  }
                  /*stmt is DeclareStatement -> {
                      stmt.declarations.forEach { decl ->
                          if(decl.dataType is EntryType && decl.name.equals(target,true)){
                              val rng = createRange(stmt.position!!)
                              locations.add(Location(uri,rng))
                          }
                      }
                  }*/
              }
          }
      }
      return locations
    }
    private fun resolveProcedureDeclaration(uri: String, ast: PLIProgram, target: String?): List<Location> {
      val locations : MutableList<Location> = mutableListOf()

      ast.walk().forEach { stmt ->
          when {
              stmt is ProcedureDeclaration -> {
                  if(target.equals(stmt.label,true)) {
                      val rng = createRange(stmt.position!!)
                      locations.add(Location(uri,rng))
                  }
              }
              stmt is DeclareStatement -> {
                  stmt.declarations.forEach { decl ->
                      if(decl.dataType is EntryType && decl.name.equals(target,true)){
                          val rng = createRange(stmt.position!!)
                          locations.add(Location(uri,rng))
                      }
                  }
              }
          }
      }
      return locations
    }

  fun resolveIdentifier(ast: CompilationUnit, name: String, uri: String,target : Identifier ) :  List<Location> {
      /* Splits the names */
      val members = name.split(".").reversed()
      val results : MutableList<Location> = mutableListOf()
      val candidates : MutableList<DataDeclaration> = mutableListOf()

      val proc = target.findAncestorOfType(ProcedureDeclaration::class.java)
      if(proc != null) {
          proc.params.forEach { param ->
              if(param.name.equals(target.name,true)) {
                  val rng = createRange(param.position!!)
                  results.add(Location(uri,rng))
                  return results
              }
          }
      }

      ast.walkDescendants(DataDeclaration::class)
              .filter {
                  it.name.equals(members[0],true)
              }.forEach {
                  candidates.add(it)
              }
          candidates.forEach { it ->
              val symbols : MutableList<String> = mutableListOf(it.name)
              it.walkAncestors().filter { (it is DataDeclaration) }.forEach { parent ->
                  val x = parent  as DataDeclaration
                  symbols.add(0,parent.name)
              }
              if(symbols.containsAll(members)) {
                  LOG.debug("name: $name ${symbols.joinToString(".")}")
                  val rng = createRange(it.position!!)
                  results.add(Location(uri,rng))
              }
          }

      return results
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

  fun createRange(pos: com.strumenta.kolasu.model.Position) : Range {
    return Range(
        Position(pos.start.line - 1, pos.start.column -1),
        Position(pos.end.line - 1, pos.end.column -1),
    )
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

}
