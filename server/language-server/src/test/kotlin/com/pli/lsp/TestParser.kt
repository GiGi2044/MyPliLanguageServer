package com.pli.compiler.ls.langserver

import com.pli.compiler.parser.PLIParserFacade

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

import java.io.File
import java.net.URI

class TestParser {

    @Test
    fun testURI() {
        val uri = URI("file:///Volumes/development/heirloom/customer/bmw/mmrs_refactoring/src/test/preprocessed/testinit.pli")
        PLIParserFacade.parse(File(uri.path))
        println("test")

    }
}
