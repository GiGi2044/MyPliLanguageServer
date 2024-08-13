package com.pli.ls.langserver

import java.net.URI
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.*
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier
import java.io.Closeable
import java.io.File
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PliLanguageServer : LanguageServer, LanguageClientAware, Closeable {
      // Initialize logger
    private val logger: Logger = LoggerFactory.getLogger(PliLanguageServer::class.java)

    private val textDocumentService = PliTextDocumentService(this)
    private val workspaceService = PliWorkspaceService(this)
    private var client: LanguageClient? = null
    private var errorCode = 1

    override fun connect(languageClient: LanguageClient) {
      this.client = languageClient
      logger.info("Connected to Language Client: $client")
      workspaceService.connect(languageClient)
      textDocumentService.connect(languageClient)
    }

    override fun initialize(initializeParams: InitializeParams?): CompletableFuture<InitializeResult> {
        val initializeResult = InitializeResult(ServerCapabilities())
        initializeResult.capabilities.textDocumentSync = Either.forLeft(TextDocumentSyncKind.Full)
        val completionOptions = CompletionOptions()
        initializeResult.capabilities.completionProvider = completionOptions
        initializeResult.capabilities.definitionProvider = true // Corrected to a Boolean

        if (initializeParams?.rootUri != null) {
          logger.info("Adding workspace ${initializeParams.rootUri} to source path")

          val extensions = listOf( "pli" )
          val workspacePath: URI = URI(initializeParams.rootUri)

          File("${workspacePath.path}").walk().forEach { sourceFile ->
              if(extensions.contains(sourceFile.extension)) {
                  workspaceService.sources.add("${workspacePath}${File.separator}${sourceFile.name}")
              }
          }
          workspaceService.sources.sort()
        textDocumentService.parse(workspaceService.sources.joinToString("\n"))
        }
        return CompletableFuture.supplyAsync { initializeResult }
    }

    override fun shutdown(): CompletableFuture<Any> {
        logger.info("Shutting down PL/1 Language Server")

        close()
        errorCode = 0
        return CompletableFuture.completedFuture(null)
    }

    override fun close() {
      textDocumentService.close()
      workspaceService.close()
      logger.info("close")
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
}
