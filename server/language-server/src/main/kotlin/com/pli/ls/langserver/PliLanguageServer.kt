package com.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.*
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PliLanguageServer : LanguageServer, LanguageClientAware {
      // Initialize logger
    private val logger: Logger = LoggerFactory.getLogger(PliLanguageServer::class.java)

    private val textDocumentService = PliTextDocumentService(this)
    private val workspaceService = PliWorkspaceService(this)
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
        logger.info("Shutting down PL/1 Language Server")

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
        logger.info("Connected to Language Client: $client")
    }
}
