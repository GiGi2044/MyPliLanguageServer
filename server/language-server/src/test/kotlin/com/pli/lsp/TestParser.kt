//package com.pli.lsp
//
//import com.pli.compiler.parser.PLIKParser
//import org.junit.Test
//import java.io.File
//import java.net.URI
//import com.pli.compiler.parser.PLIParserFacade
//import com.pli.compiler.parser.ast.*
//import com.strumenta.kolasu.model.*
//import org.eclipse.lsp4j.*
//import org.eclipse.lsp4j.Position
//import org.eclipse.lsp4j.jsonrpc.messages.Either
//
//class TestParser {
//    fun createRange(pos: com.strumenta.kolasu.model.Position): Range {
//        return Range(
//            Position(pos.start.line - 1, pos.start.column - 1),
//            Position(pos.end.line - 1, pos.end.column - 1),
//        )
//    }
//
//    private fun resolveStructureMembers(decl: DataDeclaration, level: Int = 0): MutableList<DocumentSymbol> {
//        val members: MutableList<DocumentSymbol> = mutableListOf()
//        if (decl.children != null) {
//            decl.children!!.forEach { member ->
//                when {
//                    member.dataType is StructureType -> {
//                        println("${member.name}")
//                        val rng1 = createRange(decl.position!!)
//                        val sp = DocumentSymbol("${member.name}", SymbolKind.Struct, rng1, rng1)
//                        sp.children = resolveStructureMembers(member, level + 1)
//                        members.add(sp)
//                    }
//                    else -> {
//                        println("${member.name}")
//                        val rng1 = createRange(decl.position!!)
//                        val sp = DocumentSymbol("${member.name}", SymbolKind.Variable, rng1, rng1)
//                        members.add(sp)
//                    }
//                }
//            }
//        }
//        return members
//    }
//
//    fun resolveSymbol(
//        uri: String,
//        procedure: ProcedureDeclaration
//    ): MutableList<Either<SymbolInformation, DocumentSymbol>> {
//        val result: MutableList<Either<SymbolInformation, DocumentSymbol>> = mutableListOf()
//        try {
//            val rng = Range(
//                Position(procedure.position!!.start.line - 1, procedure.position!!.start.column - 1),
//                Position(procedure.position!!.end.line - 1, procedure.position!!.end.column - 1),
//            )
//            val si = DocumentSymbol(procedure.label!!, SymbolKind.Function, rng, rng)
//            result.add(Either.forRight<SymbolInformation, DocumentSymbol>(si))
//
//            val params: MutableList<DocumentSymbol> = mutableListOf()
//            procedure.params.forEach { param ->
//                val sp = DocumentSymbol(param.name, SymbolKind.Variable, rng, rng)
//                params.add(sp)
//            }
//            si.children = params
//            procedure.statements.forEach { stmt ->
//                when {
//                    stmt is ProcedureDeclaration -> {
//                        result.addAll(resolveSymbol(uri, stmt))
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return result
//    }
//
//    //@Test
//    //fun testURI() {
//    //    val uri =
//    //        URI("file:///Volumes/development/heirloom/pli_compiler2.old/core/src/test/resources/transpiler/bmw/mini/pli/P1NPST.pli")
//    //    val parser = PLIKParser()
//    //    val parseTree = parser.parse(File(uri.path), considerPosition = false)
////
////
//    //    if (parseTree.correct) {
//    //        val result: MutableList<Either<SymbolInformation, DocumentSymbol>> = mutableListOf()
//    //        val ast = parseTree.root!!
////
////
////
//    //        ast.walk().forEach { stmt ->
//    //            when {
//    //                stmt is ProcedureDeclaration -> {
//    //                    result.addAll(resolveSymbol(uri.toString(), stmt))
////
//    //                    stmt.statements.forEach { stmt ->
//    //                        when {
//    //                            stmt is DeclareStatement -> {
//    //                                stmt.declarations.forEach { decl ->
//    //                                    if (decl.dataType !is EntryType) {
//    //                                        val rng1 = createRange(stmt.position!!)
//    //                                        when {
//    //                                            decl.dataType is StructureType -> {
//    //                                                try {
//    //                                                    println("${decl.name} (S)")
//    //                                                    val sp = DocumentSymbol(
//    //                                                        "${decl.name}",
//    //                                                        SymbolKind.Struct,
//    //                                                        rng1,
//    //                                                        rng1
//    //                                                    )
//    //                                                    sp.children = resolveStructureMembers(decl)
//    //                                                    //params.add(sp)
//    //                                                } catch (e: Exception) {
//    //                                                    //LOG.error(e.stackTraceToString())
//    //                                                }
//    //                                            }
//    //                                            else -> {
//    //                                                val sp =
//    //                                                    DocumentSymbol("${decl.name}", SymbolKind.Variable, rng1, rng1)
//    //                                                //params.add(sp)
//    //                                            }
//    //                                        }
////
//    //                                    }
//    //                                }
//    //                            }
//    //                        }
//    //                    }
//    //                }
////
//    //            }
//    //        }
////
//    //        ast.walk().forEach { stmt ->
//    //            when {
//    //                stmt is DeclareStatement -> {
//    //                    stmt.declarations.forEach { decl ->
//    //                        if (decl.dataType is EntryType) {
//    //                            println(decl.name)
//    //                        }
//    //                    }
//    //                }
//    //            }
//    //        }
//    //        ast.walk().forEach { stmt ->
//    //            when {
//    //                stmt is DeclareStatement -> {
//    //                    stmt.declarations.forEach { decl ->
//    //                        if (decl.dataType !is EntryType) {
//    //                            println(decl.name)
//    //                        }
//    //                    }
//    //                }
//    //            }
//    //        }
////
//    //        val line = 8 + 1
//    //        val character = 13 + 1
//    //        ast.walk().forEach { stmt ->
//    //            if (stmt.position != null) {
//    //                if (stmt.position!!.start.line == line) {
//    //                    when {
//    //                        stmt is DeclareStatement -> {
//    //                            stmt.declarations.forEach { decl ->
//    //                                if (decl.dataType is EntryType) {
////
//    //                                }
//    //                            }
//    //                        }
//    //                        stmt is ProcedureDeclaration -> {
//    //                            ast.walk().forEach { s ->
//    //                                when {
//    //                                    s is CallStatement -> {
//    //                                        if (stmt.label.equals(s.target, true)) {
//    //                                            //println(s.target)
//    //                                        }
//    //                                    }
//    //                                }
//    //                            }
//    //                        }
//    //                    }
//    //                }
//    //            }
//    //        }
////
//    //    } else {
//    //        parseTree.issues.forEach { error ->
//    //            println(error)
//    //        }
//    //    }
////
//    //}
//
//    //@Test
//    //fun testPosition() {
//    //    val uri =
//    //        URI("file:///Volumes/development/heirloom/pli_compiler2.old/core/src/test/resources/transpiler/bmw/mini/pli/P1NPST.pli")
////
//    //    val parser = PLIKParser()
//    //    val parsed = parser.parse(File(uri.path), considerPosition = false)
////
//    //    if (parsed.correct) {
//    //        val result: MutableList<Either<SymbolInformation, DocumentSymbol>> = mutableListOf()
//    //        val ast = parsed.root!!
//    //        val point = Point(6546,10)
//    //        val target = ast.walk().filter { it.position != null && it.position!!.contains(point)  }.lastOrNull()
////
//    //        when {
//    //            target != null && target is Identifier -> {
////
//    //                val candidates : MutableList<DataDeclaration> = mutableListOf()
//    //                val proc = target.findAncestorOfType(ProcedureDeclaration::class.java)
//    //                if(proc != null) {
//    //                    proc.params.forEach { param ->
//    //                        if(param.name.equals(target.name,true)) {
//    //                            println("${target.name}")
//    //                        }
//    //                    }
//    //                }
////
//    //                var x = target
//    //                val full = mutableListOf<String>()
//    //                while (x!!.parent != null && x is Identifier) {
//    //                    full.add(0,x.name)
//    //                    x = x.parent
//    //                }
//    //                println(full.joinToString("."))
//    //            }
//    //        }
////
//    //        ast.walk().filter { it.position != null && it.position!!.contains(point) }.forEach { stmt ->
//    //            if(stmt.position != null) {
//    //                if(stmt.position!!.contains(point))  {
//    //                    println(stmt.javaClass)
//    //                }
//    //            }
////
//    //        }
//    //    }
//    //}
//}
