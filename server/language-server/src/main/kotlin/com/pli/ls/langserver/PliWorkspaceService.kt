package com.pli.ls.langserver

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.services.WorkspaceService
import java.util.concurrent.CompletableFuture

class PliWorkspaceService : WorkspaceService {
    override fun symbol(workspaceSymbolParams: WorkspaceSymbolParams?): CompletableFuture<List<SymbolInformation>> {
        return CompletableFuture.completedFuture(emptyList())
    }

    override fun didChangeConfiguration(didChangeConfigurationParams: DidChangeConfigurationParams?) {}

    override fun didChangeWatchedFiles(didChangeWatchedFilesParams: DidChangeWatchedFilesParams?) {}
}
