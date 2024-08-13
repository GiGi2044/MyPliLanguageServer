package com.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.DidChangeConfigurationParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.WorkspaceService
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.io.Closeable
import java.util.concurrent.CompletableFuture

class PliWorkspaceService(val server : PliLanguageServer,val sources : MutableList<String> = mutableListOf()) : WorkspaceService, LanguageClientAware, Closeable {
  private val logger: Logger = LoggerFactory.getLogger(PliLanguageServer::class.java)

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams) {
      logger.info("didChangeWatchedFiles")
    }

    override fun didChangeWorkspaceFolders(params: DidChangeWorkspaceFoldersParams) {
      logger.info("didChangeWorkspaceFolders")
    }
    override fun didChangeConfiguration(params: DidChangeConfigurationParams) {
      logger.info("didChangeConfiguration")
    }

    override fun connect(client: LanguageClient) {
      logger.info("connected to workspace")
    }

    override fun close() {
      logger.info("closing workspace")
    }

    override fun symbol(params: WorkspaceSymbolParams): CompletableFuture<MutableList<out SymbolInformation>> {
        TODO()
    }

}
