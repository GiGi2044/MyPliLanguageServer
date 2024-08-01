package org.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.TextDocumentService
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

class PliTextDocumentService : TextDocumentService {
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

    override fun didOpen(didOpenTextDocumentParams: DidOpenTextDocumentParams?) {}

    override fun didChange(didChangeTextDocumentParams: DidChangeTextDocumentParams?) {}

    override fun didClose(didCloseTextDocumentParams: DidCloseTextDocumentParams?) {}

    override fun didSave(didSaveTextDocumentParams: DidSaveTextDocumentParams?) {}
}
