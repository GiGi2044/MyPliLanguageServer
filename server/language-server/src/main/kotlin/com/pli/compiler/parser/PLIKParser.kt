package com.pli.compiler.parser


import com.pli.compiler.parser.ast.Statement
import com.pli.compiler.parser.ast.toAst
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Position
import com.strumenta.kolasu.parsing.KolasuParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Lexer
import org.antlr.v4.runtime.TokenStream
import java.io.InputStream


data class CompilationUnit(
    val stmts: List<Statement>,
    val filename: String,
    override val specifiedPosition: Position? = null
) : Node(specifiedPosition) { }

class PLIKParser : KolasuParser<CompilationUnit, PLIParser, PLIParser.ProgramContext>() {
    override fun createANTLRLexer(inputStream: InputStream): Lexer {
        return PLILexer(CharStreams.fromStream(inputStream))
    }

    override fun createANTLRParser(tokenStream: TokenStream): PLIParser {
        return PLIParser(tokenStream)
    }

    override fun invokeRootRule(parser: PLIParser): PLIParser.ProgramContext? {
        return parser.program()
    }

    override fun parseTreeToAst(parseTreeRoot: PLIParser.ProgramContext, considerPosition: Boolean): CompilationUnit? {
        return parseTreeRoot.toAst("")
    }
}