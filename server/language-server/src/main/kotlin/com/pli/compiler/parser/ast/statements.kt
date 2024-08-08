package com.pli.compiler.parser.ast

import com.pli.compiler.parser.PLIParser
import com.strumenta.kolasu.mapping.toPosition
import com.strumenta.kolasu.model.*
import com.pli.compiler.utils.LOG
//import com.pli.ls.langserver.Index
//import com.pli.ls.langserver.ProcedureEntry
import com.strumenta.kolasu.mapping.position
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.pli.compiler.parser.CompilationUnit

enum class PLIType(val typename: String) {
    BIT("BIT"),
    ENTRY("ENTRY"),
    PICTURE("PICTURE"),
    POINTER("POINTER"),
    ARITHMETIC("ARITHMETIC"),
    CHARACTER("CHARACTER"),
    STRUCTURE("STRUCTURE"),
    FILE("FILE"),
    BUILTIN("BUILTIN"),
    CUSTOM("CUSTOM")
}

enum class Storage(val storage: String) {
    BASED("BASED"), /* Dynamic allocation               */
    STATIC("STATIC"), /* Variables in global frame        */
    DEFINED("BASED"), /* Defined */
    AUTOMATIC("AUTOMATIC"), /* Variables in local frame         */
    CONTROLLED("CONTROLLED") /* Dynamic allocation ALLOCATE/FREE */
}

enum class ScopeOption(val scope: String) {
    EXTERNAL("EXTERNAL"),
    INTERNAL("INTERNAL"),
}

enum class Base(val base: String) {
    FIXED("FIXED"),
    FLOAT("FLOAT"),
}
enum class Scale(val scale: String) {
    BINARY("BINARY"),
    DECIMAL("DECIMAL"),
}


enum class FileOption(val option: String) {
    /* TODO double check */
    INPUT("INPUT"),
    OUTPUT("OUTPUT"),
    UPDATE("UPDATE"),
    PRINT("PRINT"),
    STREAM("STREAM"),
    RECORD("RECORD"),
    SEQUENTIAL("SEQUENTIAL"),
    DIRECT("DIRECT"),
    BUFFERED("BUFFERED"),
    UNBUFFERED("BUFFERED"),
    SYSIN("SYSIN"),
    SYSOUT("SYSOUT"),
    SYSERR("SYSERR"),
    KEYED("KEYED"),

}
enum class DoFlavor(val type: String) {
    UNKNOWN("UNKNOWN"),     /* Unknown/unsupported loop type */
    WHILE("WHILE"),         /* Used to represent the DO WHILE (EXP) UNTIL (EXP) */
    UNTIL("UNTIL"),         /* Used to represent the DO UNTIL (EXP) WHILE (EXP) */
    FOR("FOR"),             /* Used to represent the DO I = EXP TO EXP BY */
    UPTHRU("UPTHRU"),       /* Used to represent the DO I = EXP UPTHRU */
    DOWNTHRU("DOWNTHRU"),   /* Used to represent the DO I = EXP DOWNTHRU */
    REPEAT("REPEAT"),       /* Used to represent the DO I = EXP REPEAT */
    DOLIST("LIST"),       /* Used to represent the DO I = EXP,EXP,EXP;*/
}

data class PLIProject(val programs : List<PLIProjectProgram>)
data class PLIProjectProgram(val name : String, val ast : PLIProgram)

data class PLIProgram(val statements: List<Statement>, val filename: String, override val specifiedPosition: Position? = null) : Node(specifiedPosition)
abstract class Statement(open val conditions: List<String>, open val label: String?, override val specifiedPosition: Position? = null) : Node(specifiedPosition)
data class PackageDeclaration(override val conditions: List<String>,val statements: List<Statement>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class ProcedureDeclaration(val params: List<DataDeclaration>, val options: List<String>, val returns: DataType?, val statements: List<Statement>, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class DeclareStatement(val declarations: MutableList<DataDeclaration>, override val conditions: List<String>, override val label: String? = null, val filename: String,override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class AssigmentStatement(val identifiers: List<Identifier>, val expression: Expression,  val byName: Boolean, override val conditions: List<String>, override val label: String? = null, val filename: String,override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class CompoundAssigmentStatement(val identifiers: List<Identifier>, val operator: String, val expression: Expression,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class CallStatement(val target: String, val params: List<Expression>, override val conditions: List<String>, override val label: String? = null, val filename: String,override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class ReturnStatement(val expression: Expression?, var datatype: DataType?, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class EndStatement(override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class EntryStatement(override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)

data class BeginStatement(val statements: List<Statement>, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class IfStatement(val expression: Expression, val thenStatements: List<Statement>, val elseStatements: List<Statement>,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class DoStatementType1(val statements: List<Statement>, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class DoStatementType2(val flavor: DoFlavor, val expressions: List<Expression>, val statements: List<Statement>,  override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
//data class DoStatementType3(val identifier: Expression?, val expression: Expression?, val expressions: List<Expression>, val expressionTo: Expression?, val expressionBy: Expression?, val expressionWhile: Expression?, val statements: List<Statement>, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class DoStatementType3(val flavor: DoFlavor,val identifier: Expression,val specifications: List<DoStatementType3Spec>,val statements: List<Statement>,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
open class DoStatementType3Spec(open val exp1: Expression, override val specifiedPosition: Position? = null) : Node(specifiedPosition)
data class DoStatementType3List(override val exp1: Expression, val expressions: List<Expression>, val extra: DoStatementType3Extras?, override val specifiedPosition: Position? = null) : DoStatementType3Spec(exp1,specifiedPosition)
data class DoStatementType3To(override val exp1: Expression,val to: Expression,val by: Expression?,val extra: DoStatementType3Extras?,override val specifiedPosition: Position? = null) : DoStatementType3Spec(exp1,specifiedPosition)
data class DoStatementType3UpThru(override val exp1: Expression,val upthru: Expression,val extra: DoStatementType3Extras?,override val specifiedPosition: Position? = null) : DoStatementType3Spec(exp1,specifiedPosition)
data class DoStatementType3DownThru(override val exp1: Expression, val downthru: Expression,val extra: DoStatementType3Extras?,override val specifiedPosition: Position? = null) : DoStatementType3Spec(exp1,specifiedPosition)
data class DoStatementType3Repeat(override val exp1: Expression,val repeat : Expression,val extra: DoStatementType3Extras?,override val specifiedPosition: Position? = null) : DoStatementType3Spec(exp1,specifiedPosition)
data class DoStatementType3Extras(val expWhile: Expression?,val expUntil : Expression?,override val specifiedPosition: Position? = null) : Node(specifiedPosition)


data class DoStatementType4(val statements: List<Statement>, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class LeaveStatement(val labelToLeave: String, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)

data class SelectStatement(val expression: Expression? = null, val cases: List<SelectWhenStatement>, val otherwise: Statement? = null, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class SelectWhenStatement(val expressions: List<Expression>, val statement: Statement, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class OnStatement(val condition: String,val snap: Boolean,val system: Boolean,val statements: List<Statement>,val params: List<Expression>,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class OpenFile(val files : List<Identifier>, val options : List<FileOption>, val title: Expression? = null,val linesize: Expression? = null,val pagesize: Expression? = null,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class CloseFile(val files : List<Identifier>,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class ReadFile(val file : Identifier, val into: Expression,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class WriteFile(val file : Identifier, val from: Expression,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)

data class DataDeclaration(var name: String, val dataType: DataType?, val dimensions: List<Expression>, val scopeOption: ScopeOption?, val environmentName: String?, val children: MutableList<DataDeclaration>? = null, val filename: String, override val conditions: List<String>, override val label: String? = null, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class OrdinalDeclaraion(val type : Identifier, val values : List<Identifier>,val code: String, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class ArithmeticDeclaration(var name: String, val dataType: DataType?, val dimensions: List<Expression>, val scopeOption: ScopeOption?, val environmentName: String?, val children: MutableList<DataDeclaration>? = null, val filename: String, override val conditions: List<String>, override val label: String? = null, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class CharacterDeclaration(var name: String, val dataType: DataType?, override val conditions: List<String>, override val label: String? = null, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class InitializationValue(val repeater: Expression? = null, val value: Expression, override val specifiedPosition: Position? = null) : Node(specifiedPosition)

data class GotoStatement(val target: String,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class NopStatement(override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class SignalStatement(var condition: String,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class RevertStatement(var condition: String,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class NoteStatement(val message : Expression, val code : Expression, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
// TODO

data class PutStatement(val file : Expression?,val string : Expression?,val dataSpecification: List<Expression>,val edits: List<Expression>,val formats: List<EditFormat>, val skip : Expression? ,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
//open class DataSpecification(val expression: Expression,override val specifiedPosition: Position? = null) : Node(specifiedPosition)
data class GetStatement(val file : Expression?,val dataSpecification: List<Expression>,val edits: List<Expression>,val formats: List<EditFormat>,override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)

open class Symbol(open val name: String, val type: DataType, override val specifiedPosition: Position? = null) : Node(specifiedPosition)

// data class BuiltInDecl(override var name: String, override val specifiedPosition: Position? = null) : Symbol(name,specifiedPosition)

open class DataType(open val storage: Storage, open val initialization: MutableList<InitializationValue>, override val specifiedPosition: Position? = null) : Node(specifiedPosition)
data class ArithmeticType(var base: Base, val scale: Scale, val precision: Int, val decimals: Int, override val storage: Storage, val constant: Expression? = null, override val initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class BitType(val size: Expression, val constant: Expression? = null, override var storage: Storage, override var initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class CharacterType(val size: Expression, val constant: Expression? = null, override var storage: Storage, val varying: Boolean = false, val varyingz: Boolean = false, val locator: Expression? = null, val locatorPosition: Expression? = null, override val initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class PictureType(val picture: Expression, val constant: Expression? = null, override var storage: Storage,val locator: Expression? = null, val varying: Boolean = false, val varyingz: Boolean = false, override val initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class StructureType(val level: Int, val like : Identifier? = null, override var storage: Storage, val locator: Expression?, override val initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class FileType(override var storage: Storage, val options : List<FileOption>,val varying: Boolean = false, val varyingz: Boolean = false, override val initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class BuiltinType(override var storage: Storage, val varying: Boolean = false, val varyingz: Boolean = false, override val initialization: MutableList<InitializationValue> = mutableListOf(), override val specifiedPosition: Position? = null) : DataType(storage, initialization, specifiedPosition)
data class EntryType(override val storage: Storage,override val initialization: MutableList<InitializationValue>,override val specifiedPosition: Position? = null) : DataType(storage,initialization,specifiedPosition)
data class PointerType(override val storage: Storage,override val initialization: MutableList<InitializationValue>,override val specifiedPosition: Position? = null) : DataType(storage,initialization,specifiedPosition)

open class EditFormat(override val specifiedPosition: Position? = null) : Node(specifiedPosition)
data class EditFormatA(val subscript : Expression? = null,override val specifiedPosition: Position? = null) : EditFormat(specifiedPosition)
data class EditFormatP(val subscript : Expression? = null,override val specifiedPosition: Position? = null) : EditFormat(specifiedPosition)
data class EditFormatX(val subscript : Expression? = null,override val specifiedPosition: Position? = null) : EditFormat(specifiedPosition)
data class EditFormatF(val subscript : List<Expression>,override val specifiedPosition: Position? = null) : EditFormat(specifiedPosition)
data class EditFormatCOL(val subscript : Expression,override val specifiedPosition: Position? = null) : EditFormat(specifiedPosition)

data class Comment(val comment: String, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class Inject(val code: String, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class ExecCICS(val code: String, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)
data class ExecSQL(val code: String, override val conditions: List<String>, override val label: String? = null, val filename: String, override val specifiedPosition: Position? = null) : Statement(conditions, label, specifiedPosition)

val PARSER_LOG: Logger = LoggerFactory.getLogger("parser")

fun findDataStruct(ast: PLIProgram, name: String): DataDeclaration? {
    ast.walk().forEach { declaration ->
        when {
            declaration is DataDeclaration && declaration.dataType is StructureType -> {
                if(declaration.name.equals(name)) {
                    return declaration
                }
            }
        }
    }
    return null
}

private fun Node.mergeComments(comments: MutableList<Comment>) {

    this.processProperties {
        if (it.value == null) {
            acceptComment(this, comments)
        } else if (it.multiple) {
            (it.value as Collection<*>).map { (it as Node).mergeComments(comments) }
        } else {
            if (it.provideNodes) {
                (it.value as Node).mergeComments(comments)
            } else {
            }
        }
    }
}

fun acceptComment(node: Node, comments: MutableList<Comment>) {
    comments.map {
        if (node.position != null) {
            if (isBefore(it, node)) {
                println("$node $it")
            }
        }
    }
}

fun isBefore(comment: Comment, node: Node): Boolean {
    if (comment.position!!.start.line == node.position!!.start.line) {
        return comment.position!!.end.column < node.position!!.start.column
    }
    return node.position!!.start.line < node.position!!.start.line
}


fun PLIParser.ProgramContext.toAst(filename: String): CompilationUnit {
    var statements: MutableList<Statement> = mutableListOf()
    var global: HashMap<String, Symbol> = HashMap<String, Symbol>()

    val symbolTable: MutableList<HashMap<String, Symbol>> = mutableListOf(global)
    this.stmt().map {
        statements.add(it.toAst(filename, symbolTable))
    }
    val cu = CompilationUnit(stmts = statements,filename = filename, specifiedPosition = toPosition())
    return cu
}

private fun PLIParser.StmtContext.toAst(filename: String, symbolTable: MutableList<HashMap<String, Symbol>>): Statement {
    val label = if (this.label() != null) this.label().text.removeSuffix(":") else null
    val conditions: MutableList<String> = mutableListOf()
    //this.conditions().map {
    //    conditions.add(it.text)
    //}

    if(this.statement() != null) {
        return this.statement().toAst(filename, conditions, label, symbolTable)
    } else {
        return NopStatement(conditions = conditions, label = label, filename = filename)
    }
}

private fun PLIParser.StatementContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<HashMap<String, Symbol>>): Statement {
    when {
        this.packageStmt() != null -> return this.packageStmt().toAst(filename, conditions, label)
        this.entryStmt() != null -> return this.entryStmt().toAst(filename, conditions, label)
        this.declareStmt() != null -> return this.declareStmt().toAst(filename, conditions, label, symbolTable)
        this.procedureStmt() != null -> return this.procedureStmt().toAst(filename, conditions, label, symbolTable)
        this.beginStmt() != null -> return this.beginStmt().toAst(filename, conditions, label, symbolTable)
        this.assignStmt() != null -> return this.assignStmt().toAst(filename, conditions, label, symbolTable)
        this.callStmt() != null -> return this.callStmt().toAst(filename, conditions, label, symbolTable)
        this.returnStmt() != null -> return this.returnStmt().toAst(filename, conditions, label, symbolTable)
        this.ifStmt() != null -> return this.ifStmt().toAst(filename, conditions, label, symbolTable)
        this.doStmtType1() != null -> return this.doStmtType1().toAst(filename, conditions, label, symbolTable)
        this.doStmtType2() != null -> return this.doStmtType2().toAst(filename, conditions, label, symbolTable)
        this.doStmtType3() != null -> return this.doStmtType3().toAst(filename, conditions, label, symbolTable)
        this.doStmtType4() != null -> return this.doStmtType4().toAst(filename, conditions, label, symbolTable)
        this.leaveStmt() != null -> return this.leaveStmt().toAst(filename, conditions, label, symbolTable)
        this.selectStmt() != null -> return this.selectStmt().toAst(filename, conditions, label, symbolTable)
        this.onStmt() != null -> return this.onStmt().toAst(filename, conditions, label, symbolTable)
        this.openFileStmt() != null -> return this.openFileStmt().toAst(filename, conditions, label, symbolTable)
        this.closeFileStmt() != null -> return this.closeFileStmt().toAst(filename, conditions, label, symbolTable)
        this.readFileStmt() != null -> return this.readFileStmt().toAst(filename, conditions, label, symbolTable)
        this.writeFileStmt() != null -> return this.writeFileStmt().toAst(filename, conditions, label, symbolTable)
        this.putStmt() != null -> return this.putStmt().toAst(filename, conditions, label, symbolTable)
        this.getStmt() != null -> return this.getStmt().toAst(filename, conditions, label, symbolTable)
        this.nopStmt() != null -> return this.nopStmt().toAst(filename, conditions, label, symbolTable)
        this.signalStmt() != null -> return this.signalStmt().toAst(filename, conditions, label, symbolTable)
        this.revertStmt() != null -> return this.revertStmt().toAst(filename, conditions, label, symbolTable)
        this.gotoStmt() != null -> return this.gotoStmt().toAst(filename, conditions, label, symbolTable)
        this.stopStmt() != null -> return this.stopStmt().toAst(filename, conditions, label, symbolTable)
        this.noteStmt() != null -> return this.noteStmt().toAst(filename, conditions, label, symbolTable)
        // this.inject() != null -> return this.inject().toAst(filename, conditions, label, symbolTable)
        // bmw
        this.defineStmt() != null -> return this.defineStmt().toAst(filename, conditions, label, symbolTable)
        this.execSQL() != null -> return this.execSQL().toAst(filename, conditions, label, symbolTable)
        this.execCICS() != null -> return this.execCICS(). toAst(filename, conditions, label, symbolTable)
        this.defRange() != null -> return  this.defRange().toAst(filename, conditions, label, symbolTable)


        else -> {
            LOG.error("Unsupported Statement  ${this.text}")
            error("Unsupported Statement  ${this.text}")
        }
    }
}

private fun PLIParser.DefRangeContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
) : Statement {

    return NopStatement(conditions = conditions, label = label, filename = filename)
}

private fun PLIParser.BeginStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    val statements: MutableList<Statement> = mutableListOf()

    this.stmt().map {
        statements.add(it.toAst(filename, symbolTable))
    }

    return BeginStatement(statements = statements,conditions = conditions, label = label, filename = filename,specifiedPosition = toPosition())
}

private fun PLIParser.ExecCICSContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {

    return ExecCICS(code = this.CICS_BODY().text, filename = filename, conditions = conditions, label = label, specifiedPosition = toPosition())
}

private fun PLIParser.ExecSQLContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    //println(start.startPoint)
    return ExecSQL(code = this.SQL_BODY().text, filename = filename, conditions = conditions, label = label, specifiedPosition = Position(start = start.startPoint, end = start.endPoint) )
}

private fun PLIParser.DefineStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    if(this.ORDINAL() != null) {
        val type = this.identifier().toAst()
        val values : MutableList<Identifier> = mutableListOf()
        this.ordinalValues().identifier().forEach() { id ->
            values.add(id.toAst())
        }
        return OrdinalDeclaraion( type = type, values = values, code ="", conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    }
    if(this.ALIAS() != null) {
        val dt = parseAttributes(this.attributes(), symbolTable = symbolTable)
        symbolTable.first().put(this.identifier().text,Symbol(this.identifier().text,dt))
    }
    //println("Unsupported Statement  ${this.text}")
    return NopStatement(conditions = conditions, label = label, filename = filename)
}

private fun PLIParser.NoteStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val message = this.expression(0).toAst()
    val code = this.expression(1).toAst()

    return NoteStatement(message,code,conditions = conditions, label = label, filename = filename)
}

private fun PLIParser.StopStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    return NopStatement(conditions = conditions, label = label, filename = filename)
}

private fun PLIParser.GotoStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val target = this.label().text
    return GotoStatement(target = target,conditions = conditions, label = label, filename = filename)
}

/* ON statement */
private fun PLIParser.OnStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    var condition = "UNKNOWN"
    val statements: MutableList<Statement> = mutableListOf()
    val params: MutableList<Expression> = mutableListOf()

    if(this.onCondition() != null) {
        if(this.onCondition().condition != null) {
            condition = this.onCondition().condition.text
            this.onCondition().expression().forEach { exp ->
                params.add(exp.toAst())
            }
        } else {
            LOG.warn("Unsupported on condition")
        }
    }

    if(this.stmt() != null) {
        statements.add(this.stmt().toAst(filename,symbolTable))
    }

    if(this.beginStmt() != null) {
        this.beginStmt().stmt().map {
            statements.add(it.toAst(filename, symbolTable))
        }

    }
    return OnStatement(condition =  condition, snap = (this.SNAP() != null) , system = this.SYSTEM() != null, statements = statements, conditions = conditions, params = params, label = label, filename = filename, specifiedPosition = toPosition())
}

/* SIGNAL statement */
private fun PLIParser.SignalStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    val condition = this.conditions().text

    return SignalStatement(condition = condition,conditions = conditions, label = label, filename = filename)
}

/* REVERT statement */
private fun PLIParser.RevertStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val condition = this.conditions().text

    return RevertStatement(condition = condition,conditions = conditions, label = label, filename = filename,specifiedPosition = toPosition())
}

private fun PLIParser.NopStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    return NopStatement(conditions = conditions, label = label, filename = filename)
}

private fun PLIParser.InjectContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    return Inject(code = this.text, conditions = conditions, label = label, filename = filename)
}

private fun PLIParser.GetStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    var file : Expression? = null
    val formats : MutableList<EditFormat> = mutableListOf()
    val edits : MutableList<Expression> = mutableListOf()
    val dataSpecification: MutableList<Expression> = mutableListOf()

    this.getOption().forEach { opt ->
        when {
            opt.dataSpecification() != null -> {
                opt.dataSpecification().expression().map { s ->
                    dataSpecification.add(s.toAst())
                }
                if(opt.dataSpecification().editDataList() != null) {
                    opt.dataSpecification().editDataList().expression().forEach { exp ->
                        edits.add(exp.toAst())
                    }
                }
                if(opt.dataSpecification().editFormatList() != null) {
                    opt.dataSpecification().editFormatList().editFormat().forEachIndexed { index,exp ->
                        val fmt = parseEditFormat(exp)
                        when {
                            fmt is EditFormatCOL -> {
                                val id = COL(fmt.subscript)
                                edits.add(index,id)
                            }
                        }

                        formats.add(fmt)
                    }
                }
            }
            opt.FILE_() != null -> file = opt.expression().toAst()

        }


    }

    val stmt = GetStatement(file = file, dataSpecification = dataSpecification, edits = edits, formats  = formats, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this
    return stmt

}

private fun PLIParser.PutStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    var skip : Expression? = null
    var file : Expression? = null
    var string : Expression? = null
    val formats : MutableList<EditFormat> = mutableListOf()
    val edits : MutableList<Expression> = mutableListOf()
    val dataSpecification: MutableList<Expression> = mutableListOf()

    this.putOption().forEach { opt ->
        when {
            opt.dataSpecification() != null -> {
                opt.dataSpecification().expression().map { s ->
                    dataSpecification.add(s.toAst())
                }
                if(opt.dataSpecification().editDataList() != null) {
                    opt.dataSpecification().editDataList().expression().forEach { exp ->
                        edits.add(exp.toAst())
                    }
                }
                if(opt.dataSpecification().editFormatList() != null) {
                    opt.dataSpecification().editFormatList().editFormat().forEachIndexed { index,exp ->
                        val fmt = parseEditFormat(exp)
                        when {
                            fmt is EditFormatCOL -> {
                                val id = COL(fmt.subscript)
                                edits.add(index,id)
                            }
                        }

                        formats.add(fmt)
                    }
                }
            }
            opt.FILE_() != null -> file = opt.expression().toAst()
            opt.STRING() != null -> string = opt.expression().toAst()
            opt.SKIP_() != null -> {
                if(opt.skipExpression() != null) {
                    skip = opt.skipExpression().expression().toAst()
                } else {
                    skip = IntegerLiteral("1")
                }
            }
        }
    }


    val stmt = PutStatement(file = file, string = string, dataSpecification = dataSpecification, edits = edits, formats  = formats, skip = skip, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this
    return stmt
}

fun parseEditFormat(format: PLIParser.EditFormatContext?) : EditFormat {

   if(format!!.expression().identifier() != null) {
       val identifer= format!!.expression().identifier().toAst()
        when {
           identifer.name.startsWith("A",true) -> {
               if(identifer.subscripts.isEmpty()) {
                   return EditFormatA()
               } else {
                   val subscript = identifer.subscripts[0]
                   return EditFormatA(subscript)
               }
            }

            identifer.name.startsWith("P",true) -> {
               if(identifer.subscripts.isEmpty()) {
                   return EditFormatP()
               } else {
                   val subscript = identifer.subscripts[0]
                   return EditFormatP(subscript)
               }
            }
            identifer.name.startsWith("X",true) -> {
               if(identifer.subscripts.isEmpty()) {
                   return EditFormatX()
               } else {
                   val subscript = identifer.subscripts[0]
                   return EditFormatX(subscript)
               }
            }

            identifer.name.startsWith("F",true) -> {
                val subscripts : MutableList<Expression> = mutableListOf()
                identifer.subscripts.forEach {
                    subscripts.add(it)
                }
                return EditFormatF(subscripts)
            }

            identifer.name.startsWith("COL",true) -> {
               if(identifer.subscripts.isEmpty()) {
                   return EditFormatCOL(IntegerLiteral("0"))
               } else {
                   val subscript = identifer.subscripts[0]
                   return EditFormatCOL(subscript)
               }

            }
            else -> error("unsupported edit format ${identifer.name}")

       }
    }
     error("invalid format")
}

private fun PLIParser.EntryStmtContext.toAst(filename: String, conditions: List<String>, label: String?): Statement {
    return EntryStatement(conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
}

private fun PLIParser.CommentContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<HashMap<String, Symbol>>
): Comment {
    return Comment(comment = this.COMMENT().text, filename = filename, conditions = conditions, label = label, specifiedPosition = toPosition())
}

/**
 * Package statement
 * The label represents the package name
 */
private fun PLIParser.PackageStmtContext.toAst(filename: String, condition: List<String>, label: String?): PackageDeclaration {
    var global: HashMap<String, Symbol> = HashMap<String, Symbol>()

    val symbolTable: MutableList<HashMap<String, Symbol>> = mutableListOf(global)
    val statements: MutableList<Statement> = mutableListOf()

    this.stmt().map {
        statements.add(it.toAst(filename = filename,symbolTable = symbolTable ))
    }

    return PackageDeclaration(statements = statements,conditions = condition, label = label, filename = filename, specifiedPosition = toPosition())
}

/**
 *  Procedure statement
 *  The label represents the procedure name
 *  Options are MAIN,REENTRANT; MAIN make the procedure the default entry point
 *  If the procedure has parameters by default is assigned the default type (FIXED BIN(15)
 *
 *  CALC:  PROC (A,B);  /* Parameters A and B are (FIXED BIN(15) */
 *
 *  If DECLARE statements in the procedure body matches the parameter names
 *
 *  CALC:  PROC (A,B);
 *    DECLARE (A,B) FIXED BINARY (31); /* Parameters A and B are (FIXED BIN(31) */
 *
 */
private fun PLIParser.ProcedureStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<HashMap<String, Symbol>>): ProcedureDeclaration {
    var returns: DataType? = null
    val params: MutableList<DataDeclaration> = mutableListOf()
    val options: MutableList<String> = mutableListOf()
    val statements: MutableList<Statement> = mutableListOf()

    var scope: HashMap<String, Symbol> = HashMap<String, Symbol>()
    symbolTable.add(scope)

    /* Grabs the options */
    if (this.procedureOptions() != null) {
        this.procedureOptions().procedureOption().map {
            options.add(it.text.toLowerCase())
        }
    }

    /* Return value */
    if (this.procedureReturns() != null) {
            returns = parseAttributes(procedureReturns().attributes(), symbolTable = symbolTable)
    }

    /* Grabs the parameters, assign the default data type */

    if (this.procedureParams() != null) {
        /*  Default data type */
        val defaulDataType = ArithmeticType(base = Base.FIXED, scale = Scale.BINARY, precision = 15, decimals = 0, storage = Storage.AUTOMATIC)
        val dimensions: MutableList<Expression> = mutableListOf()
        this.procedureParams().procedureParam().map {
            val dcl =  DataDeclaration(name = it.text,
                dataType = defaulDataType,
                dimensions = dimensions,
                scopeOption = null,
                environmentName = null,
                filename = filename,
                conditions = conditions,
                label = label,
                specifiedPosition = toPosition())
            dcl.parseTreeNode = it
            params.add(dcl)
        }
    }

    /* Process the procedure body statements to detect declarations that matches the parameters name */
    this.stmt().map {
        val statement = it.toAst(filename, symbolTable)
        when {
            statement is DeclareStatement -> {

                val remove : MutableList<DataDeclaration> = mutableListOf()
                statement.declarations.map { declaration ->
                /* Detects if the declarations refers to a parameter */
                    var matched = false
                    params.forEachIndexed { index, param ->
                        /* Replace the defalut with the actual declaration */
                        if (param.name.equals(declaration.name,true) ) {
                            params[index] = declaration
                            matched = true
                        }
                    }
                    if(!matched) {
                        remove.add(declaration)
                    }

                }
                /* If the declare statement is not a param add to the statement list */
                statement.declarations.clear()
                statement.declarations.addAll(remove)
                statements.add(statement)

            //if (!matched) {
                //    statements.add(statement)
                //} else {}
            }

            /* Set the return statement data type */
            statement is ReturnStatement -> {
                statement.datatype = returns
                statements.add(statement)
            }
            /* add the statement to the procedure body */
            else -> {
                statements.add(statement)
            }
        }
    }

    symbolTable.removeLast()

    val position =  toPosition()
    val adjusted = Position(
        Point(position!!.start.line,1),
        position.end
    )
    val prcedure = ProcedureDeclaration(
            params = params,
            returns = returns,
            options = options,
            statements = statements,
            conditions = conditions,
            label = label,
            filename = filename,
            specifiedPosition =adjusted)
    //Index.addProcedure(label!!, ProcedureEntry("file://$filename",prcedure))
    //Index.add("file://$filename",prcedure)
    return prcedure
}

/**
 * Declare Statement
 * If the data declarations does not specifies the type assigns the default FIXED BIN(15)
 *
 * DECLARE a;       /* A is (FIXED BIN(15) */
 *
 * Factored declaration can be used to declare many variables
 *
 * DECLARE (a,b);   /* A and B are (FIXED BIN(15) */
 *
 * Attributes are used to specify the datatype
 *
 * DECLARE a FIXED BIN(31);       /* A is (FIXED BIN(31) */
 *
 * @See parseAttributes
 */

private fun PLIParser.DeclareStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<HashMap<String, Symbol>>): Statement {

    val declarations: MutableList<DataDeclaration> = mutableListOf()

    /* Detects if it is requires to parse a data structure declaration */
    var structure: DataDeclaration? = parseStructDeclaration(this.declaration(), filename, conditions, label, symbolTable, toPosition())
    this.declaration().map {
        /* Process the attributes and overrides the default */
        val dataType = parseAttributes(it.attributes(), it.level(), symbolTable)
        val level = if (it.level() != null) it.level().text.toInt() else 0

        /* Single delcaration */
        /* #KEYWORD# issue */
        if (it.name().identifier() != null) {
            val dimensions: MutableList<Expression> = mutableListOf()
            /* TODO Patch: a Builtin function (i.e. FIXED) can appear in the BUILTIN declaration */
            //var name = if (it.name().identifier().IDENTIFIER() != null) it.name().identifier().IDENTIFIER().text else it.name().identifier().text
            var name = parseName(it.name().identifier())
            val xx = it.name().identifier().toAst()

            /* the subscript contains the array dimensions */
            it.name().identifier().subscript().map { dimension ->
                dimensions.add(dimension.expression().toAst())
            }
            /* PATCH if has dimension, remove the (111) from the name, applies to keywords */
            if(name.indexOf('(') > -1) {
                name = name.substring(0,name.indexOf('('))
            }

            if (structure != null) {
                if (level > 1) {
                    /* if processing a data structure add the declaration as a member, consider only
                  minor structures and elementary names are declared with level-numbers greater than 1. */
                    val parent = getParentDeclaration(structure, level)
                    when {
                        parent != null && dataType is StructureType -> {
                            val children: MutableList<DataDeclaration> = mutableListOf()
                            val dcl =   DataDeclaration(
                                name = name,
                                dataType = dataType,
                                dimensions = dimensions,
                                scopeOption = ScopeOption.INTERNAL,
                                environmentName = null,
                                children = children,
                                filename = filename,
                                conditions = conditions,
                                label = label,
                                specifiedPosition = parent.position
                            )
                            dcl.parseTreeNode = it
                            parent.children!!.add( dcl )
                        }
                        else -> {
                            val dcl =  DataDeclaration(
                                name = name,
                                dataType = dataType,
                                dimensions = dimensions,
                                scopeOption = ScopeOption.INTERNAL,
                                environmentName = null,
                                filename = filename,
                                conditions = conditions,
                                label = label,
                                specifiedPosition = xx.position
                            )
                            dcl.parseTreeNode = it
                            parent.children!!.add(dcl)
                        }
                    }
                }
            } else {
                val dcl =  DataDeclaration(
                    name = name,
                    dataType = dataType,
                    dimensions = dimensions,
                    scopeOption = ScopeOption.INTERNAL,
                    environmentName = null,
                    filename = filename,
                    conditions = conditions,
                    label = label,
                    specifiedPosition = toPosition()
                )
                dcl.parseTreeNode = it
                declarations.add(dcl)
            }
        }

        /* Factored declaration */
        if (it.name().factoredNames() != null) {
            val dimensions: MutableList<Expression> = mutableListOf()
            /* #KEYWORD issue */
            it.name().factoredNames().identifier().map {
                /* TODO Patch: a Builtin function (i.e. FIXED) can appear in the BUILTIN declaration */
                //var name = if (it.IDENTIFIER() != null) it.IDENTIFIER().text else it.text

                var name = parseName(it)
                it.subscript().map { dimension ->
                    dimensions.add(dimension.expression().toAst())
                }
                val dcl = DataDeclaration(
                    name = name,
                    dataType = dataType,
                    dimensions = dimensions,
                    scopeOption = ScopeOption.INTERNAL,
                    environmentName = null,
                    filename = filename,
                    conditions = conditions,
                    label = label,
                    specifiedPosition = toPosition()
                )
                dcl.parseTreeNode = it
                declarations.add(dcl)
            }
        }
    }

    if (structure != null) {
        declarations.add(structure)
    }

/*
    declarations.forEach {
        if(it.dataType is ArithmeticType) {
            return ArithmeticDeclaration(name = it.name, dataType = it.dataType, dimensions = it.dimensions, scopeOption = it.scopeOption, environmentName = it.environmentName, filename = it.filename, conditions = conditions, label = label)
        }
        if(it.dataType is CharacterType) {
            return CharacterDeclaration(name = it.name, dataType = it.dataType, conditions = conditions, label = label)
        }

    }*/
    return DeclareStatement(declarations = declarations, conditions = conditions, label = label, filename = filename,specifiedPosition = toPosition())
}

fun parseName(identifier: PLIParser.IdentifierContext): String {

    if(identifier.identifier() != null) {
        return identifier.identifier().text
    }

    if(identifier.simpleIdentifier() != null) {
        return identifier.simpleIdentifier().idOrKeyword()
    }
    error("${identifier.text}")

}


/**
 * Assigment statement
 *
 * The condition specifies the enabling or disabling of a PL/I condition
 * The label is one statement labels, it identifies a statement so that it can be referred
 * to at some other point in the program
 */
private fun PLIParser.AssignStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<HashMap<String, Symbol>>): Statement {
    val identifiers: MutableList<Identifier> = mutableListOf()
    val scope = symbolTable.last()

    /* variables name */
    this.identifier().map {
        /* TODO Patch: a Builtin function (i.e. FIXED) can appear in the BUILTIN declaration */
        //var name = if (it.IDENTIFIER() != null) it.IDENTIFIER().text else it.text
        var name = parseName(it)

        identifiers.add(it.toAst() as Identifier)
    }
    var byName = false
    if(this.assignByName()!= null) {
        if(assignByName().BY() != null && assignByName().NAME() != null)
            byName = true
    }
    val expression = this.expression().toAst()


    return when {
        this.EQUAL() != null ->  {
            val stmt = AssigmentStatement(identifiers = identifiers, expression = expression, byName = byName , conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
            stmt.parseTreeNode = this
            stmt
        }
        this.PLUSEQUAL() != null -> CompoundAssigmentStatement(identifiers = identifiers, operator = "+=", expression = expression, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
        this.MINUSEQUAL() != null -> CompoundAssigmentStatement(identifiers = identifiers, operator = "=-", expression = expression, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
        this.STAREQUAL() != null -> CompoundAssigmentStatement(identifiers = identifiers, operator = "*=", expression = expression, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
        this.SLASHEQUAL() != null -> CompoundAssigmentStatement(identifiers = identifiers, operator = "/=", expression = expression, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())

        else -> error("compound assigned not supported error")
    }
}


/**
 * CALL statement
 */
private fun PLIParser.CallStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val target = if(this.callTarget().IDENTIFIER() != null)  this.callTarget().IDENTIFIER().text else this.callTarget().INITIAL_().text

    var params: MutableList<Expression> = mutableListOf()

    this.expression().map {
        params.add(it.toAst())
    }

    return CallStatement(target = target, params = params, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
}

/**
 * RETURN statement
 * The value returned is the value of the expression specified, converted to conform to the
 * attributes specified in the RETURNS option
 */

private fun PLIParser.ReturnStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    var expression: Expression? = null
    if (this.expression() != null) {
        expression = this.expression().toAst()
    }
    return ReturnStatement(expression = expression, datatype = null, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
}

/**
 * IF statement
 */
private fun PLIParser.IfStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    var thenStmt: MutableList<Statement> = mutableListOf()
    var elseStmt: MutableList<Statement> = mutableListOf()

    val expression = this.expression().toAst()

    if (this.stmt(0) != null) {
        thenStmt.add(this.stmt(0).toAst(filename, symbolTable))
    }

    if (this.ELSE() != null) {
        elseStmt.add(this.stmt(1).toAst(filename, symbolTable))
    }

    val stmt = IfStatement(expression = expression, thenStatements = thenStmt, elseStatements = elseStmt, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this.expression()
    return stmt
}

/**
 * DO statement Type 1
 */
private fun PLIParser.DoStmtType1Context.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val statements: MutableList<Statement> = mutableListOf()

    this.stmt().forEach {
        statements.add(it.toAst(filename, symbolTable))
    }

    val stmt = DoStatementType1(
            statements = statements,
            conditions = conditions,
            label = label,
            filename = filename,
            specifiedPosition = toPosition())
    stmt.parseTreeNode = this
    return stmt
}

/**
 * DO statement Type 2
 */
private fun PLIParser.DoStmtType2Context.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val expressions: MutableList<Expression> = mutableListOf()
    val statements: MutableList<Statement> = mutableListOf()
    var flavor: DoFlavor = DoFlavor.UNKNOWN

    if (dwexp1 != null) {
        flavor = DoFlavor.WHILE
        expressions.add(dwexp1.toAst())
        if (dwexp2 != null) {
            expressions.add(dwexp2.toAst())
        }
    }

    if (duexp1 != null) {
        flavor = DoFlavor.UNTIL
        expressions.add(duexp1.toAst())
        if (duexp2 != null) {
            expressions.add(duexp2.toAst())
        }
    }

    this.stmt().forEach {
        statements.add(it.toAst(filename, symbolTable))
    }

    val stmt = DoStatementType2(
        flavor = flavor,
        expressions = expressions,
        statements = statements,
        conditions = conditions,
        label = label,
        filename = filename,
        specifiedPosition = toPosition())
    stmt.parseTreeNode = this
    return stmt
}

/**
 * DO statement Type 3
 */

private fun PLIParser.DoStmtType3Context.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    var flavor: DoFlavor = DoFlavor.UNKNOWN
    val statements: MutableList<Statement> = mutableListOf()
    val expressions: MutableList<Expression> = mutableListOf()
    val specifications: MutableList<DoStatementType3Spec> = mutableListOf()

    var extra : DoStatementType3Extras? = null
    val identifier: Expression = this.identifier().toAst()

    // Process the specifications
    this.doStmtSpecification().map {
        val exp1 = it.exp1.toAst()


        // Extra WHILE / UNTIL
        if(it.doStmtExtra() != null) {
            val ext = it.doStmtExtra()

            // UNTIL ( exp5 ) WHILE ( exp4 )
            if(ext.doStmtExtraUntil() != null) {
                var whileExp : Expression? = null
                val untilExp = ext.doStmtExtraUntil().expUntil.toAst()
                if(ext.doStmtExtraUntil().doStmtExtraWhile() != null) {
                    whileExp = ext.doStmtExtraUntil().doStmtExtraWhile().expWhile.toAst()
                }
                extra = DoStatementType3Extras(expUntil = untilExp,expWhile = whileExp)

            }

            // WHILE ( exp4 ) UNTIL ( exp5 )
            if(ext.doStmtExtraWhile() != null) {
                var untilExp : Expression? = null
                val whileExp = ext.doStmtExtraWhile().expWhile.toAst()
                if(ext.doStmtExtraWhile().doStmtExtraUntil() != null) {
                    untilExp = ext.doStmtExtraWhile().doStmtExtraUntil().expUntil.toAst()
                }

                extra = DoStatementType3Extras(expWhile = whileExp, expUntil = untilExp)
            }

        }
        when {
            /* TO exp2 (BY exp3) */
            it.TO() != null -> {
                flavor = DoFlavor.FOR

                var by: Expression? = null
                val to: Expression = it.expTo.toAst()
                // By is optional
                if( it.expBy != null) {
                    by = it.expBy.toAst()
                }

                specifications.add(DoStatementType3To(exp1 = exp1,to = to, by = by, extra = extra))
            }
            /* UPTHRU exp2 */
            it.UPTHRU() != null -> {
                flavor = DoFlavor.UPTHRU

                val upthru : Expression = it.expUp.toAst()
                specifications.add(DoStatementType3UpThru(exp1 = exp1,upthru = upthru, extra = extra))
            }
            /* DOWNTHRU exp2 */
            it.DOWNTHRU() != null -> {
                flavor = DoFlavor.DOWNTHRU

                val downthru : Expression = it.expDown.toAst()
                specifications.add(DoStatementType3DownThru(exp1 = exp1,downthru = downthru,extra = extra))

            }
            /* REPEAT exp2 */
            it.REPEAT() != null -> {
                flavor = DoFlavor.REPEAT

                val repeat : Expression = it.expRepeat.toAst()
                specifications.add(DoStatementType3Repeat(exp1 = exp1,repeat = repeat,extra = extra))
            }
            /* exp1 */
            else -> {
                flavor = DoFlavor.DOLIST
                // Collects the expressions
                expressions.add( it.exp1.toAst() )
            }
        }

    }

    /** Process the expression list */
    if( flavor == DoFlavor.DOLIST && expressions.isNotEmpty()) {
        specifications.add(DoStatementType3List(exp1 =  expressions[0], expressions = expressions,extra = extra))
    }

    // Body
    this.stmt().forEach {
        statements.add(it.toAst(filename, symbolTable))
    }

    val stmt = DoStatementType3(
        flavor =  flavor,
        identifier = identifier,
        specifications = specifications,
        statements = statements,
        conditions = conditions,
        filename = filename
    )
    stmt.parseTreeNode = this
    return stmt
}

/**
 * DO statement Type 4
 */

private fun PLIParser.DoStmtType4Context.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    val statements: MutableList<Statement> = mutableListOf()

    this.stmt().forEach {
        statements.add(it.toAst(filename, symbolTable))
    }

    return DoStatementType4(
        statements = statements,
        filename = filename,
        label = label,
        conditions = conditions
    )
}

/**
 * LEAVE statement
 */

private fun PLIParser.LeaveStmtContext.toAst(
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<java.util.HashMap<String, Symbol>>
): Statement {
    var label = ""
    if(this.label() != null) {
        label = this.label().text
    }
    return LeaveStatement(
        labelToLeave = label,
        filename = filename,
        label = label,
        conditions = conditions
    )
}

/**
 * SELECT statement
 */
private fun PLIParser.SelectStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<HashMap<String, Symbol>>): Statement {
    var otherwise: Statement? = null
    var expression: Expression? = null
    val cases: MutableList<SelectWhenStatement> = mutableListOf()

    if (this.expression() != null) {
        expression = this.expression().toAst()
        this.selectWhen().map {
            val expressions: MutableList<Expression> = mutableListOf()
            it.expression().map { e ->
                expressions.add(e.toAst())
            }
            val _when = SelectWhenStatement(expressions, it.stmt().toAst(filename, symbolTable), conditions, label, filename, specifiedPosition = toPosition())
            cases.add(_when)
        }

        if (this.selectOtherwise() != null) {
            if (this.selectOtherwise().stmt() != null) {
                otherwise = this.selectOtherwise().stmt().toAst(filename, symbolTable)
            }
        }
        return SelectStatement(expression = expression, cases = cases, otherwise = otherwise, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())


    } else {

        this.selectWhen().map {
            val expressions: MutableList<Expression> = mutableListOf()
            it.expression().map { e ->
                expressions.add(e.toAst())
            }
            val _when = SelectWhenStatement(expressions, it.stmt().toAst(filename, symbolTable), conditions, label, filename, specifiedPosition = toPosition())
            cases.add(_when)
        }

        if (this.selectOtherwise() != null) {
            if (this.selectOtherwise().stmt() != null) {
                otherwise = this.selectOtherwise().stmt().toAst(filename, symbolTable)
            }
        }

        return SelectStatement(expression = expression, cases = cases, otherwise = otherwise, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())

    }

}

/**
 * OPEN FILE statement
 */
private fun PLIParser.OpenFileStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    var title :Expression? = null
    var linesize :Expression? = null
    var pagesize :Expression? = null
    val options : MutableList<FileOption> = mutableListOf()
    val files : MutableList<Identifier> = mutableListOf()

    this.file().forEach { f ->
        files.add(f.identifier().toAst())
    }
    if(this.title() != null) {
        title = this.title().expression().toAst()
    }
    if(this.linesize() != null) {
        title = this.linesize().expression().toAst()
    }
    if(this.pagesize() != null) {
        title = this.pagesize().expression().toAst()
    }

    this.openFileOpts().forEach {
        when {
            it.INPUT() != null -> options.add(FileOption.INPUT)
            it.OUTPUT() != null -> options.add(FileOption.OUTPUT)
            it.UPDATE() != null -> options.add(FileOption.UPDATE)
            it.PRINT() != null -> options.add(FileOption.PRINT)
            it.STREAM() != null -> options.add(FileOption.STREAM)
            it.RECORD() != null -> options.add(FileOption.RECORD)
            it.SEQUENTIAL() != null -> options.add(FileOption.SEQUENTIAL)
            it.DIRECT() != null -> options.add(FileOption.DIRECT)
            it.BUFFERED() != null -> options.add(FileOption.BUFFERED)
            it.PRINT() != null -> options.add(FileOption.PRINT)
            it.KEYED() != null -> options.add(FileOption.KEYED)
        }
    }

    val stmt = OpenFile(files = files,options = options,title = title,linesize = linesize, pagesize = pagesize  ,conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this

    return stmt
}

/**
 * CLOSE FILE statement
 */
private fun PLIParser.CloseFileStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val files : MutableList<Identifier> = mutableListOf()

    this.file().forEach { f ->
        files.add(f.identifier().toAst())
    }

    val stmt = CloseFile(files = files,conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this

    return stmt
}

/**
 * READ FILE statement
 */
private fun PLIParser.ReadFileStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val file = this.file().identifier().toAst()
    val into = this.expression().toAst()

    val stmt = ReadFile(file = file, into = into, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this

    return stmt
}

/**
 * WRITE FILE statement
 */
private fun PLIParser.WriteFileStmtContext.toAst(filename: String, conditions: List<String>, label: String?, symbolTable: MutableList<java.util.HashMap<String, Symbol>>): Statement {
    val file = this.file().identifier().toAst()
    val from = this.expression(0).toAst()


    val stmt = WriteFile(file = file, from = from, conditions = conditions, label = label, filename = filename, specifiedPosition = toPosition())
    stmt.parseTreeNode = this

    return stmt
}

/**
 *  Parse the attributes and returns a datatype
 *
 */

fun parseAttributes(
    attributes: List<PLIParser.AttributesContext>,
    level: PLIParser.LevelContext? = null,
    symbolTable: MutableList<HashMap<String, Symbol>>
): DataType {

    var base = Base.FIXED
    var scale = Scale.BINARY
    var storage = Storage.AUTOMATIC
    var decimals = 0
    var precision = 15
    var locator: Expression? = null
    var position: Expression? = null
    var constant: Expression? = null
    var size: Expression = IntegerLiteral("1")
    var picture: Expression = StringLiteral("999")
    var varying = false
    var varyingz = false
    var like : Identifier? = null
    val fileOptions : MutableList<FileOption> = mutableListOf()

    val initialization: MutableList<InitializationValue> = mutableListOf()

    var type = if (level != null) PLIType.STRUCTURE else PLIType.ARITHMETIC
    var custom = ""

    attributes.map {
        when {
            /* Arithmetic */
            it.data() != null && it.data().FIXED() != null -> {
                base = Base.FIXED
                type = PLIType.ARITHMETIC
            }
            it.data() != null && it.data().FLOAT() != null -> {
                base = Base.FLOAT
                type = PLIType.ARITHMETIC
            }
            it.data() != null && it.data().BINARY() != null -> {
                scale = Scale.BINARY
                type = PLIType.ARITHMETIC
            }
            it.data() != null && it.data().DECIMAL() != null -> {
                scale = Scale.DECIMAL
                type = PLIType.ARITHMETIC
            }
            /* Character */
            it.data() != null && (it.data().CHARACTER() != null || it.data().VARCHAR() != null) -> {
                type = PLIType.CHARACTER
                if (it.data().expression() != null) {
                    size = it.data().expression().toAst()
                }

                if (it.data().VARYING().size != 0) {
                    varying = true
                }

                if (it.data().VARYINGZ().size != 0) {
                    varyingz = true
                }
            }
            /* BIT */
            it.data() != null && it.data().BIT() != null -> {
                type = PLIType.BIT
                if (it.data().expression() != null) {
                    size = it.data().expression().toAst()
                }
            }
            /* PICTURE */
            it.data() != null && it.data().PICTURE() != null -> {
                type = PLIType.PICTURE
                if (it.data().expression() != null) {
                    picture = it.data().expression().toAst()
                }
            }
            /* FILE */
            it.data() != null && it.data().FILE_() != null -> {
                type = PLIType.FILE
            }
            /* BUILTIN */
            it.data() != null && it.data().BUILTIN() != null -> {
                type = PLIType.BUILTIN
            }
            /* LIKE */
            it.data() != null && it.data().LIKE() != null -> {
                like = it.data().like.toAst()
            }
            /* BASED */
            it.data() != null && it.data().BASED() != null -> {
                storage = Storage.BASED
                locator = it.data().expression().toAst()
            }
            /* POINTER */
            it.data() != null && it.data().POINTER() != null -> {
                type = PLIType.POINTER
            }
            /* Entry */
            it.entry() != null -> {
                type = PLIType.ENTRY
            }
            /* File options */
            it.data() != null && it.data().INPUT() != null -> {
                fileOptions.add(FileOption.INPUT)
            }
            it.data() != null && it.data().OUTPUT() != null -> {
                fileOptions.add(FileOption.OUTPUT)
            }
            it.data() != null && it.data().UPDATE() != null -> {
                fileOptions.add(FileOption.UPDATE)
            }
            it.data() != null && it.data().PRINT() != null -> {
                fileOptions.add(FileOption.PRINT)
            }
            it.data() != null && it.data().STREAM() != null -> {
                fileOptions.add(FileOption.STREAM)
            }
            it.data() != null && it.data().RECORD() != null -> {
                fileOptions.add(FileOption.RECORD)
            }
            it.data() != null && it.data().SEQUENTIAL() != null -> {
                fileOptions.add(FileOption.SEQUENTIAL)
            }
            it.data() != null && it.data().DIRECT() != null -> {
                fileOptions.add(FileOption.DIRECT)
            }
            it.data() != null && it.data().BUFFERED() != null -> {
                fileOptions.add(FileOption.BUFFERED)
            }
            it.data() != null && it.data().UNBUFFERED() != null -> {
                fileOptions.add(FileOption.UNBUFFERED)
            }
            it.data() != null && it.data().SYSIN() != null -> {
                fileOptions.add(FileOption.SYSIN)
            }
            it.data() != null && it.data().SYSOUT() != null -> {
                fileOptions.add(FileOption.SYSOUT)
            }
            it.data() != null && it.data().SYSERR() != null -> {
                fileOptions.add(FileOption.SYSERR)
            }
            it.data() != null && it.data().TYPE() != null -> {
                // it is an alias, lookup the symbol table
                type = PLIType.CUSTOM
                custom = it.data().customType().text
            }


        }
        /* Init values */
        if (it.initialization() != null) {
            it.initialization().initValue().map { i ->
                val repeater: Expression? = if (i.repetitionFactor() != null && i.repetitionFactor().repeater != null) i.repetitionFactor().repeater.toAst() else null
                if(i.expression() != null) {
                    initialization.add(InitializationValue(repeater, i.expression().toAst()))
                }
            }
        }
        /* Value */
        if(it.constant() != null) {
            if(it.constant().expression() !=null) {
                initialization.add(InitializationValue(value = it.constant().expression().toAst()))
            }
        }

        /* Precision and decimals */
        if (it.data() != null && it.data().precision() != null) {
            precision = it.data().precision().INTEGER_LITERAL().text.toInt()
            if (it.data().precision().decimals() != null) {
                decimals = it.data().precision().decimals().text.toInt()
            }
        }
        if (it.constant() != null) {
            constant = it.constant().expression().toAst()
        }
        /* Storage */
        if (it.storage() != null) {
            if (it.storage().STATIC() != null) {
                storage = Storage.STATIC
            }
            if (it.storage().BASED() != null) {
                storage = Storage.BASED
                if (it.storage().locator() != null) {
                    locator = it.storage().locator().expression().toAst()
                }
            }
            if (it.storage().DEFINED() != null) {
                storage = Storage.DEFINED
                if (it.storage().locator() != null) {
                    locator = it.storage().locator().expression().toAst()
                }
                if (it.storage().POSITION() != null && it.storage().expression() != null) {
                    position = it.storage().expression().toAst()
                }
            }
        }
    }

    /* Custom type */
    if(type == PLIType.CUSTOM) {
        val s = symbolTable.first().get(custom)
        if(s != null) {
            type = s!!.type.toPLIType()

        } else {
            /* Not found default to ARITHMETIC */
            type = PLIType.ARITHMETIC
        }
    }

    return when {
        type == PLIType.ARITHMETIC -> ArithmeticType(base = base, scale = scale, precision = precision, decimals = decimals, constant = constant, storage = storage, initialization = initialization)
        type == PLIType.BIT -> BitType(size = size, constant = constant, storage = storage, initialization = initialization)
        type == PLIType.CHARACTER -> CharacterType(size = size, constant = constant, varying = varying, varyingz = varyingz, storage = storage, locator = locator, locatorPosition = position, initialization = initialization)
        type == PLIType.PICTURE -> PictureType(picture = picture, constant = constant, storage = storage, locator = locator, initialization = initialization)
        type == PLIType.FILE -> FileType(storage = storage, options = fileOptions, initialization = initialization)
        type == PLIType.STRUCTURE -> StructureType(level = level!!.text.toInt(), like = like, storage = storage, locator = locator)
        type == PLIType.BUILTIN -> BuiltinType(storage = storage)
        type == PLIType.ENTRY -> EntryType(storage = storage,initialization = initialization)
        type == PLIType.POINTER -> PointerType(storage = storage,initialization = initialization)
        else -> {
            /* Default data type is BINARY FIXED 15 */
            ArithmeticType(base = Base.FIXED, scale = Scale.BINARY, precision = 15, decimals = 0, constant = constant, storage = Storage.AUTOMATIC)
        }
    }
}

private fun DataType.toPLIType(): PLIType {
    return when {
        this is BitType -> PLIType.BIT
        this is CharacterType -> PLIType.CHARACTER
        else -> {
            error("unable to convert ${this.javaClass}")
        }
    }
}

/**
 * Utility function to identify data structure declarations
 * Scans the declarations to match a major structure
 *
 */
fun parseStructDeclaration(
    declarations: List<PLIParser.DeclarationContext>,
    filename: String,
    conditions: List<String>,
    label: String?,
    symbolTable: MutableList<HashMap<String, Symbol>>,
    specifiedPosition: Position?
): DataDeclaration? {

    val dimensions: MutableList<Expression> = mutableListOf()
    declarations.map {
        /* The name at level 1 is called a major structure. */
        if (it.level() != null && it.level().text.equals("1")) {
            val dataType = parseAttributes(it.attributes(), it.level(),symbolTable)

            /* #KEYWORD# issiue */
            if (it.name().identifier() != null) {
                /* #KEYWORD# issiue */
                //val name = it.name().identifier().IDENTIFIER().text
                val name = parseName(it.name().identifier())
                val children: MutableList<DataDeclaration> = mutableListOf()

                it.name().identifier().subscript().map { dimension ->
                    dimensions.add(dimension.expression().toAst())
                }

                val dcl = DataDeclaration(
                        name = name,
                        dataType = dataType,
                        dimensions = dimensions,
                        scopeOption = ScopeOption.INTERNAL,
                        environmentName = null,
                        children = children,
                        filename = filename,
                        conditions = conditions,
                        label = label,
                        specifiedPosition = it.position
                )
                dcl.parseTreeNode = it
                return dcl
            }
        }
    }
    return null
}

/**
 * Utility function to find the parent of a given structure member
 */
fun getParentDeclaration(structure: DataDeclaration, level: Int): DataDeclaration {
    val structures: MutableList<DataDeclaration> = mutableListOf(structure)

    structures.addAll(extractSecondaryStruct(structure, level))

    return structures.last()
}

fun extractSecondaryStruct(structure: DataDeclaration, level: Int): List<DataDeclaration> {
    val structures: MutableList<DataDeclaration> = mutableListOf()
    structure.children!!.forEach {
        when {
            it.dataType is StructureType -> {
                if (it.dataType.level < level) {
                    structures.add(it)
                    structures.addAll(extractSecondaryStruct(it, level))
                }
            }
        }
    }
    return structures
}
