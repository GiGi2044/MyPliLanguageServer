package com.pli.compiler.ls.langserver

import com.pli.compiler.parser.PLIParserFacade
import org.junit.Test
import java.io.File
import java.net.URI

class TestParser {

    @Test
    fun testURI() {
        val uri = URI("file:////Users/giancunningham/code/GiGi2044/LSP/hellols/hellofalks/sample/sample.pli")
        PLIParserFacade.parse(File(uri.path))
        println("test")

    }
}
