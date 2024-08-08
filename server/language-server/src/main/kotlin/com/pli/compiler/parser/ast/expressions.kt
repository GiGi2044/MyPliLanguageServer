package com.pli.compiler.parser.ast

import com.pli.compiler.parser.PLIParser
import com.strumenta.kolasu.mapping.toPosition
import com.strumenta.kolasu.model.Node
import com.strumenta.kolasu.model.Position
import com.pli.compiler.parser.CompilationUnit

//val builtInSet = setOf("multiply", "abs", "substr", "index", "hbound", "fixed", "trunc", "round","plidump")
abstract class Expression(override val specifiedPosition: Position? = null) : Node(specifiedPosition)

open class Identifier(open val name: String, val subscripts: List<Expression>, val member: Identifier? = null,override val specifiedPosition: Position? = null) : Expression(specifiedPosition)

data class FloatLiteral(val value: String, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class DecimalLiteral(val value: String, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class IntegerLiteral(val value: String, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class HexLiteral(val value: String, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class StringLiteral(val value: String, val repetitionFactor: Expression? = null, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class BitLiteral(val value: String, val repetitionFactor: Expression? = null, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class BuiltIn(val name: String, val params: List<Expression>, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class COL(val size: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)

// TODO add all literal types

data class ParenthesisExpression(var expression: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class AddExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class SubtractExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class MultiplyExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class DivideExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class PowerExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class MinusUnaryExpression(var expression: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class AndExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class OrExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class NotExpression(var expression: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class ConcatExpression(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class GreaterThan(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class GreaterOrEqualThan(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class LesserThan(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class LesserOrEqualThan(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class Equal(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)
data class NotEqual(var left: Expression, var right: Expression, override val specifiedPosition: Position? = null) : Expression(specifiedPosition)

//fun isBuiltin(symbol: String): Boolean {
//    return builtInSet.contains(symbol.toLowerCase())
//}

fun PLIParser.ExpressionContext.toAst(): Expression {
    return when {
        this.identifier() != null -> {
            this.identifier().toAst()
        }
        this.literal() != null -> decorate(this.literal().toAst(),this)
        this.PLUS() != null -> {
            if(this.expression(1) != null) {
                decorate(AddExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
            } else {
                this.expression(0).toAst()
            }
        }
        this.OPEN_PAREN() != null -> ParenthesisExpression(this.expression(0).toAst())
        this.MINUS() != null -> {
            if (this.expression(1) != null) {
                decorate(SubtractExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
            } else {
                decorate(MinusUnaryExpression(expression = this.expression(0).toAst(), specifiedPosition = toPosition()),this)
            }
        }
        this.SLASH() != null -> decorate(DivideExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
        this.STAR() != null -> decorate(MultiplyExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
        this.STARSTAR() != null -> PowerExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true))
        this.CONCAT() != null -> ConcatExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true))
        this.OR() != null -> decorate(OrExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
        this.AND() != null -> decorate(AndExpression(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
        this.NOT() != null -> decorate(NotExpression(this.expression(0).toAst()),this)
        this.EQUAL() != null -> decorate(Equal(left = this.expression(0).toAst(), right = this.expression(1).toAst(), specifiedPosition = toPosition(true)),this)
        this.comparison() != null && this.comparison().GT() != null -> decorate(GreaterThan(left = this.expression(0).toAst(), right = this.expression(1).toAst()),this)
        this.comparison() != null && this.comparison().LT() != null -> decorate(LesserThan(left = this.expression(0).toAst(), right = this.expression(1).toAst()),this)
        this.comparison() != null && this.comparison().GE() != null -> decorate(GreaterOrEqualThan(left = this.expression(0).toAst(), right = this.expression(1).toAst()),this)
        this.comparison() != null && this.comparison().LE() != null -> decorate(LesserOrEqualThan(left = this.expression(0).toAst(), right = this.expression(1).toAst()),this)
        this.comparison() != null && this.comparison().NE() != null -> decorate(NotEqual(left = this.expression(0).toAst(), right = this.expression(1).toAst()),this)

        else -> error("Invalid expression: ${this.text}")
    }
}

fun decorate(node: Node, expression: PLIParser.ExpressionContext): Expression {
    node.parseTreeNode = expression
    return node as Expression
}


fun PLIParser.LiteralContext.toAst(): Expression {
    return when {

        this.INTEGER_LITERAL() != null -> {
            IntegerLiteral(value = this.INTEGER_LITERAL().text, specifiedPosition = toPosition())
        }
        this.DECIMAL_LITERAL() != null -> {
            DecimalLiteral(value = this.DECIMAL_LITERAL().text, specifiedPosition = toPosition())
        }
        this.FLOAT_LITERAL() != null -> {
            FloatLiteral(value = this.FLOAT_LITERAL().text, specifiedPosition = toPosition())
        }
        this.BIT_LITERAL() != null -> {
            var repetitionFactor: Expression? = null //if (this.expression() != null) this.expression().toAst() else null
            BitLiteral(value = this.BIT_LITERAL().text, repetitionFactor = repetitionFactor, specifiedPosition = toPosition())
        }
        this.STRING_LITERAL() != null -> {
            var repetitionFactor: Expression? = null // if (this.expression() != null) this.expression().toAst() else null
            StringLiteral(value = this.STRING_LITERAL().text, repetitionFactor = repetitionFactor, specifiedPosition = toPosition())
        }
        this.HEX_LITERAL() != null -> {
            var repetitionFactor: Expression? = null //if (this.expression() != null) this.expression().toAst() else null
            // Convert Hex to Integer
            var tmp = this.HEX_LITERAL().text
                        .replace("'","")
                        .replace("X","")
                        .replace("\n","")
                        .replace("\r","")
                        .replace("\t","")
                        .replace(" ","")
            HexLiteral(value = "0x$tmp", specifiedPosition = toPosition())
        }
        else -> error("Unsupported type ${this.text}")
    }
}


fun PLIParser.IdentifierContext.toAst(): Identifier {
    var name = ""
    val subscripts: MutableList<Expression> = mutableListOf()

    if(this.simpleIdentifier() != null ) {
        var name = simpleIdentifier().idOrKeyword()
        if(this.subscript() != null) {
            this.subscript().map {
                subscripts.add(it.expression().toAst())
            }
        }
        val id = Identifier(name, subscripts,specifiedPosition = toPosition())
        id.parseTreeNode = this
        return id
    }


    var member: Identifier? = null
    if (this.member() != null) {
        member = this.member().toAst()
    }

    if (this.identifier() != null) {
        //println(this.identifier().text)
        if(this.identifier().simpleIdentifier() != null) {
            name = this.identifier().simpleIdentifier().idOrKeyword()
            if (this.subscript() != null) {
                this.subscript().map {
                    // TODO could be STAR
                    if(it.expression() != null) {
                        subscripts.add(it.expression().toAst())
                    }
                }
            }
        } else {
            name =  this.identifier().identifier().simpleIdentifier().text
            if (this.identifier().subscript() != null) {
                this.identifier().subscript().map {
                    subscripts.add(it.expression().toAst())
                }
            }
        }

    }
    val id = Identifier(name, subscripts,member,specifiedPosition = toPosition())
    id.parseTreeNode = this
    return id

}

fun PLIParser.SimpleIdentifierContext.idOrKeyword(): String {
    // TODO handle all keywords
    return  when {
        this.IDENTIFIER() != null ->  {
            this.IDENTIFIER().text.replace("#","_")
        }
        this.KEY() != null -> this.KEY().text
        this.OFFSET() != null -> this.OFFSET().text
        this.PAGESIZE() != null -> this.PAGESIZE().text
        this.LINESIZE() != null -> this.LINESIZE().text
        this.RESERVED() != null -> this.RESERVED().text
        this.FIXED() != null -> this.FIXED().text
        this.COPY() != null -> this.COPY().text
        this.CONDITION() != null -> this.CONDITION().text
        this.STRING() != null -> this.STRING().text
        this.DATE() != null -> this.DATE().text
        this.ERROR() != null -> this.ERROR().text
        this.VALUE() != null -> this.VALUE().text
        this.SYSIN() != null -> this.SYSIN().text
        this.NAME() != null -> this.NAME().text
        this.FROM() != null -> this.FROM().text
        this.REPLY() != null -> this.REPLY().text
        this.RECORD() != null -> this.RECORD().text
        this.SIZE() != null -> this.SIZE().text
        this.IN() != null -> this.IN().text
        this.TITLE() != null -> this.TITLE().text
        this.OPTIONS() != null -> this.OPTIONS().text
        this.FORMAT() != null -> this.FORMAT().text
        this.PRIORITY() != null -> this.PRIORITY().text
        this.SUPPORT() != null -> this.SUPPORT().text
        this.RANGE() != null -> this.RANGE().text
        this.NORMAL() != null -> this.NORMAL().text
        this.REPEAT() != null -> this.REPEAT().text
        this.CHARACTER() != null -> this.CHARACTER().text
        this.STORAGE() != null -> this.STORAGE().text
        this.PARAMETER() != null -> this.PARAMETER().text
        this.DIMENSION() != null -> this.DIMENSION().text
        this.DECLARE() != null -> this.DECLARE().text
        this.DEFAULT() != null -> this.DEFAULT().text
        this.SYSNULL() != null -> this.SYSNULL().text
        this.ORDER() != null -> this.ORDER().text
        this.ON() != null -> this.ON().text
        this.FB() != null -> this.FB().text
        this.OUTPUT() != null -> this.OUTPUT().text
        this.POINTER() != null -> this.POINTER().text
        this.POSITION() != null -> this.POSITION().text
        else -> error("unsupported keyword ${this.text}")
    }
}


private fun PLIParser.MemberContext.toAst(): Identifier {
    var name = ""
    val subscripts: MutableList<Expression> = mutableListOf()

    //println(this.text)
    var x : Identifier? = null
    if(this.identifier().member() != null) {
        val x = this.identifier().identifier().toAst()
        return Identifier(x.name,x.subscripts,this.identifier().member().toAst())
    }

    return this.identifier().toAst()
}
