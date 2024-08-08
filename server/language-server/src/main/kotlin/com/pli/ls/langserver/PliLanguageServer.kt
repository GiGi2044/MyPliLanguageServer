package com.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.*
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

class PliLanguageServer : LanguageServer, LanguageClientAware {
    private val textDocumentService: TextDocumentService = PliTextDocumentService()
    private val workspaceService: WorkspaceService = PliWorkspaceService()
    private var client: LanguageClient? = null
    private var errorCode = 1

    override fun initialize(initializeParams: InitializeParams?): CompletableFuture<InitializeResult> {
        val initializeResult = InitializeResult(ServerCapabilities())
        initializeResult.capabilities.textDocumentSync = Either.forLeft(TextDocumentSyncKind.Full)
        val completionOptions = CompletionOptions()
        initializeResult.capabilities.completionProvider = completionOptions
        initializeResult.capabilities.definitionProvider = true // Corrected to a Boolean
        return CompletableFuture.supplyAsync { initializeResult }
    }

    override fun shutdown(): CompletableFuture<Any> {
        errorCode = 0
        return CompletableFuture.completedFuture(null)
    }

    override fun exit() {
        System.exit(errorCode)
    }

    override fun getTextDocumentService(): TextDocumentService {
        return this.textDocumentService
    }

    override fun getWorkspaceService(): WorkspaceService {
        return this.workspaceService
    }

    override fun connect(languageClient: LanguageClient) {
        this.client = languageClient
    }
}
