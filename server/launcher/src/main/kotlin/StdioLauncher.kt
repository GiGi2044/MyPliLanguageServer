import org.pli.ls.langserver.PliLanguageServer
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutionException
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

object StdioLauncher {
    @JvmStatic
    @Throws(ExecutionException::class, InterruptedException::class)
    fun main(args: Array<String>) {
        LogManager.getLogManager().reset()
        val globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
        globalLogger.level = Level.OFF

        startServer(System.`in`, System.out)
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private fun startServer(`in`: InputStream, out: OutputStream) {
        val pliLanguageServer = PliLanguageServer()
        val launcher = LSPLauncher.createServerLauncher(pliLanguageServer, `in`, out)
        val client: LanguageClient = launcher.remoteProxy

        pliLanguageServer.connect(client)
        val startListening = launcher.startListening()
        startListening.get()
    }
}
