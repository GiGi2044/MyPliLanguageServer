package com.pli.compiler.parser

import com.strumenta.kolasu.model.Point
import com.strumenta.kolasu.model.Position
import com.strumenta.kolasu.validation.Issue
import com.strumenta.kolasu.validation.IssueType
import org.antlr.v4.runtime.*
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*

data class ParsingResult(val filename: String = "", val root: PLIParser.ProgramContext?, val errors: List<Issue>, val comments: PLIParser.ProgramContext? = null) {
    fun isCorrect() = errors.isEmpty() && root != null
}

data class ParsingExpressionResult( val root: PLIParser.ExpressionContext?, val errors: List<Issue>, val comments: PLIParser.ProgramContext? = null) {
    fun isCorrect() = errors.isEmpty() && root != null
}

data class PLILexerResult(val root: List<Token>, val errors: List<String>) {
    fun isCorrect() = errors.isEmpty() && !root.isEmpty()
}

fun String.toStream(charset: Charset = Charsets.UTF_8) = ByteArrayInputStream(toByteArray(charset))

object PLIParserFacade {

    fun lex(code: String, debug: Boolean = false): PLILexerResult = lex(code.toStream(), debug)

    fun lex(inputStream: InputStream, debug: Boolean = false): PLILexerResult {

        val errors = LinkedList<String>()
        val lexer = PLILexer(CharStreams.fromStream(inputStream))

        lexer.removeErrorListeners()
        lexer.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    errors.add(errorMessage)
                }
            }
        })
        val tokens = LinkedList<Token>()

        do {
            val t = lexer.nextToken()
            if (t == null) {
                break
            } else {
                tokens.add(t)
                if (debug && t.type != Token.EOF) {
                    if (t.channel != PLILexer.HIDDEN) {

                        println("D[${lexer.modeNames[lexer._mode]}] ${t.text} (${lexer.ruleNames[t.type - 1]})")
                    } else {
                        println("H[${lexer.modeNames[lexer._mode]}] ${t.text} (${lexer.ruleNames[t.type - 1]})")
                    }
                }
            }
        } while (t.type != Token.EOF)

        if (tokens.last.type != Token.EOF) {
            errors.add("Not whole input consumed" /*, tokens.last!!.endPoint.asPosition)*/)
        }

        return PLILexerResult(tokens, errors)
    }

    fun parse(code: String, file: String = ""): ParsingResult = parse(code.toStream(),file)

    fun parse(file: File): ParsingResult {
        val errors = LinkedList<Issue>()
        val inputStream = FileInputStream(file)
        val filename = file.absoluteFile.toString()

        val lexer = PLILexer(CharStreams.fromStream(inputStream))
        lexer.removeErrorListeners()

        lexer.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    //errors.add(Issue(IssueType.LEXICAL,errorMessage, Position(Point(line,charPositionInLine),Point(line,charPositionInLine))))
                }
            }
        })

        val tokenStream = CommonTokenStream(lexer)

        val parser = PLIParser(tokenStream)

        parser.removeErrorListeners()
        parser.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    //errors.add(Issue(IssueType.SYNTACTIC,errorMessage, Position(Point(line,charPositionInLine),Point(line,charPositionInLine))))
                }
            }
        })
        val root = parser.program()
        val start = System.nanoTime()

        return ParsingResult(filename = filename, root = root, errors = errors)
    }

    fun parse(inputStream: InputStream, file: String = ""): ParsingResult {
        val errors = LinkedList<Issue>()

        val lexer = PLILexer(CharStreams.fromStream(inputStream))
        lexer.removeErrorListeners()

        lexer.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    //errors.add("$ANSI_BLUE$file:$line:$charPositionInLine: $ANSI_YELLOW ERROR: $errorMessage$ANSI_RESET")
                    //errors.add(Issue(IssueType.LEXICAL,errorMessage, Position(Point(line,charPositionInLine),Point(line,charPositionInLine))))
                }
            }
        })

        val tokenStream = CommonTokenStream(lexer)

        val parser = PLIParser(tokenStream)

        parser.removeErrorListeners()
        parser.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    //errors.add((errorMessage ?: "unspecified: ") + Point(line, charPositionInLine).toString())
                    //errors.add(Issue(IssueType.SYNTACTIC,errorMessage, Position(Point(line,charPositionInLine),Point(line,charPositionInLine))))
                    //errors.add("$ANSI_BLUE$file:$line:$charPositionInLine: $ANSI_YELLOW ERROR: $errorMessage$ANSI_RESET")
                }
            }
        })

        val root = parser.program()
        //lexer.reset()
        //parser.tokenStream = CommonTokenStream(lexer, PLILexer.HIDDEN)
        //val comments = parser.program()

        return ParsingResult(filename = "", root = root, errors = errors)
    }

    fun parseExpression(inputStream: InputStream): ParsingExpressionResult {
        val errors = LinkedList<Issue>()

        val lexer = PLILexer(CharStreams.fromStream(inputStream))
        lexer.removeErrorListeners()

        lexer.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    //errors.add("$ANSI_BLUE$file:$line:$charPositionInLine: $ANSI_YELLOW ERROR: $errorMessage$ANSI_RESET")
                    //errors.add(Issue(IssueType.LEXICAL,errorMessage, Position(Point(line,charPositionInLine),Point(line,charPositionInLine))))
                }
            }
        })

        val tokenStream = CommonTokenStream(lexer)

        val parser = PLIParser(tokenStream)

        parser.removeErrorListeners()
        parser.addErrorListener(object : BaseErrorListener() {
            override fun syntaxError(p0: Recognizer<*, *>?, p1: Any?, line: Int, charPositionInLine: Int, errorMessage: String?, p5: RecognitionException?) {
                if (errorMessage != null) {
                    //errors.add((errorMessage ?: "unspecified: ") + Point(line, charPositionInLine).toString())
                    //errors.add(Issue(IssueType.SYNTACTIC,errorMessage, Position(Point(line,charPositionInLine),Point(line,charPositionInLine))))
                    //errors.add("$ANSI_BLUE$file:$line:$charPositionInLine: $ANSI_YELLOW ERROR: $errorMessage$ANSI_RESET")
                }
            }
        })
        val root = parser.expression()
        return ParsingExpressionResult(root = root, errors = errors)

    }

}
