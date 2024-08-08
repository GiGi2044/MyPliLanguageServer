// Generated from PLIParser.g4 by ANTLR 4.9.3
package com.pli.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PLIParser}.
 */
public interface PLIParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PLIParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(PLIParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(PLIParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(PLIParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(PLIParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#preconditions}.
	 * @param ctx the parse tree
	 */
	void enterPreconditions(PLIParser.PreconditionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#preconditions}.
	 * @param ctx the parse tree
	 */
	void exitPreconditions(PLIParser.PreconditionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(PLIParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(PLIParser.CommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#inject}.
	 * @param ctx the parse tree
	 */
	void enterInject(PLIParser.InjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#inject}.
	 * @param ctx the parse tree
	 */
	void exitInject(PLIParser.InjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(PLIParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(PLIParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#conditions}.
	 * @param ctx the parse tree
	 */
	void enterConditions(PLIParser.ConditionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#conditions}.
	 * @param ctx the parse tree
	 */
	void exitConditions(PLIParser.ConditionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#directive}.
	 * @param ctx the parse tree
	 */
	void enterDirective(PLIParser.DirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#directive}.
	 * @param ctx the parse tree
	 */
	void exitDirective(PLIParser.DirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#include}.
	 * @param ctx the parse tree
	 */
	void enterInclude(PLIParser.IncludeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#include}.
	 * @param ctx the parse tree
	 */
	void exitInclude(PLIParser.IncludeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#includedMember}.
	 * @param ctx the parse tree
	 */
	void enterIncludedMember(PLIParser.IncludedMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#includedMember}.
	 * @param ctx the parse tree
	 */
	void exitIncludedMember(PLIParser.IncludedMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(PLIParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(PLIParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#defRange}.
	 * @param ctx the parse tree
	 */
	void enterDefRange(PLIParser.DefRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#defRange}.
	 * @param ctx the parse tree
	 */
	void exitDefRange(PLIParser.DefRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#defExpression}.
	 * @param ctx the parse tree
	 */
	void enterDefExpression(PLIParser.DefExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#defExpression}.
	 * @param ctx the parse tree
	 */
	void exitDefExpression(PLIParser.DefExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#execSQL}.
	 * @param ctx the parse tree
	 */
	void enterExecSQL(PLIParser.ExecSQLContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#execSQL}.
	 * @param ctx the parse tree
	 */
	void exitExecSQL(PLIParser.ExecSQLContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#execCICS}.
	 * @param ctx the parse tree
	 */
	void enterExecCICS(PLIParser.ExecCICSContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#execCICS}.
	 * @param ctx the parse tree
	 */
	void exitExecCICS(PLIParser.ExecCICSContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#nopStmt}.
	 * @param ctx the parse tree
	 */
	void enterNopStmt(PLIParser.NopStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#nopStmt}.
	 * @param ctx the parse tree
	 */
	void exitNopStmt(PLIParser.NopStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(PLIParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(PLIParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#assignByName}.
	 * @param ctx the parse tree
	 */
	void enterAssignByName(PLIParser.AssignByNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#assignByName}.
	 * @param ctx the parse tree
	 */
	void exitAssignByName(PLIParser.AssignByNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#defineStmt}.
	 * @param ctx the parse tree
	 */
	void enterDefineStmt(PLIParser.DefineStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#defineStmt}.
	 * @param ctx the parse tree
	 */
	void exitDefineStmt(PLIParser.DefineStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#ordinalValues}.
	 * @param ctx the parse tree
	 */
	void enterOrdinalValues(PLIParser.OrdinalValuesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#ordinalValues}.
	 * @param ctx the parse tree
	 */
	void exitOrdinalValues(PLIParser.OrdinalValuesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#declareStmt}.
	 * @param ctx the parse tree
	 */
	void enterDeclareStmt(PLIParser.DeclareStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#declareStmt}.
	 * @param ctx the parse tree
	 */
	void exitDeclareStmt(PLIParser.DeclareStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(PLIParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(PLIParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#genericRefs}.
	 * @param ctx the parse tree
	 */
	void enterGenericRefs(PLIParser.GenericRefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#genericRefs}.
	 * @param ctx the parse tree
	 */
	void exitGenericRefs(PLIParser.GenericRefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#genericRef}.
	 * @param ctx the parse tree
	 */
	void enterGenericRef(PLIParser.GenericRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#genericRef}.
	 * @param ctx the parse tree
	 */
	void exitGenericRef(PLIParser.GenericRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#genericRefData}.
	 * @param ctx the parse tree
	 */
	void enterGenericRefData(PLIParser.GenericRefDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#genericRefData}.
	 * @param ctx the parse tree
	 */
	void exitGenericRefData(PLIParser.GenericRefDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#customType}.
	 * @param ctx the parse tree
	 */
	void enterCustomType(PLIParser.CustomTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#customType}.
	 * @param ctx the parse tree
	 */
	void exitCustomType(PLIParser.CustomTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#level}.
	 * @param ctx the parse tree
	 */
	void enterLevel(PLIParser.LevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#level}.
	 * @param ctx the parse tree
	 */
	void exitLevel(PLIParser.LevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(PLIParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(PLIParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#factoredNames}.
	 * @param ctx the parse tree
	 */
	void enterFactoredNames(PLIParser.FactoredNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#factoredNames}.
	 * @param ctx the parse tree
	 */
	void exitFactoredNames(PLIParser.FactoredNamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#attributes}.
	 * @param ctx the parse tree
	 */
	void enterAttributes(PLIParser.AttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#attributes}.
	 * @param ctx the parse tree
	 */
	void exitAttributes(PLIParser.AttributesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#data}.
	 * @param ctx the parse tree
	 */
	void enterData(PLIParser.DataContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#data}.
	 * @param ctx the parse tree
	 */
	void exitData(PLIParser.DataContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(PLIParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(PLIParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#entryParam}.
	 * @param ctx the parse tree
	 */
	void enterEntryParam(PLIParser.EntryParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#entryParam}.
	 * @param ctx the parse tree
	 */
	void exitEntryParam(PLIParser.EntryParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#precision}.
	 * @param ctx the parse tree
	 */
	void enterPrecision(PLIParser.PrecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#precision}.
	 * @param ctx the parse tree
	 */
	void exitPrecision(PLIParser.PrecisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#decimals}.
	 * @param ctx the parse tree
	 */
	void enterDecimals(PLIParser.DecimalsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#decimals}.
	 * @param ctx the parse tree
	 */
	void exitDecimals(PLIParser.DecimalsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#alignment}.
	 * @param ctx the parse tree
	 */
	void enterAlignment(PLIParser.AlignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#alignment}.
	 * @param ctx the parse tree
	 */
	void exitAlignment(PLIParser.AlignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#scope}.
	 * @param ctx the parse tree
	 */
	void enterScope(PLIParser.ScopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#scope}.
	 * @param ctx the parse tree
	 */
	void exitScope(PLIParser.ScopeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#externalName}.
	 * @param ctx the parse tree
	 */
	void enterExternalName(PLIParser.ExternalNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#externalName}.
	 * @param ctx the parse tree
	 */
	void exitExternalName(PLIParser.ExternalNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#storage}.
	 * @param ctx the parse tree
	 */
	void enterStorage(PLIParser.StorageContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#storage}.
	 * @param ctx the parse tree
	 */
	void exitStorage(PLIParser.StorageContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#locator}.
	 * @param ctx the parse tree
	 */
	void enterLocator(PLIParser.LocatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#locator}.
	 * @param ctx the parse tree
	 */
	void exitLocator(PLIParser.LocatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(PLIParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(PLIParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#initialization}.
	 * @param ctx the parse tree
	 */
	void enterInitialization(PLIParser.InitializationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#initialization}.
	 * @param ctx the parse tree
	 */
	void exitInitialization(PLIParser.InitializationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#initSpec}.
	 * @param ctx the parse tree
	 */
	void enterInitSpec(PLIParser.InitSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#initSpec}.
	 * @param ctx the parse tree
	 */
	void exitInitSpec(PLIParser.InitSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#initValue}.
	 * @param ctx the parse tree
	 */
	void enterInitValue(PLIParser.InitValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#initValue}.
	 * @param ctx the parse tree
	 */
	void exitInitValue(PLIParser.InitValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#callStmt}.
	 * @param ctx the parse tree
	 */
	void enterCallStmt(PLIParser.CallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#callStmt}.
	 * @param ctx the parse tree
	 */
	void exitCallStmt(PLIParser.CallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#callTarget}.
	 * @param ctx the parse tree
	 */
	void enterCallTarget(PLIParser.CallTargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#callTarget}.
	 * @param ctx the parse tree
	 */
	void exitCallTarget(PLIParser.CallTargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(PLIParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(PLIParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#entryStmt}.
	 * @param ctx the parse tree
	 */
	void enterEntryStmt(PLIParser.EntryStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#entryStmt}.
	 * @param ctx the parse tree
	 */
	void exitEntryStmt(PLIParser.EntryStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#arrayDimensions}.
	 * @param ctx the parse tree
	 */
	void enterArrayDimensions(PLIParser.ArrayDimensionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#arrayDimensions}.
	 * @param ctx the parse tree
	 */
	void exitArrayDimensions(PLIParser.ArrayDimensionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#arrayDimension}.
	 * @param ctx the parse tree
	 */
	void enterArrayDimension(PLIParser.ArrayDimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#arrayDimension}.
	 * @param ctx the parse tree
	 */
	void exitArrayDimension(PLIParser.ArrayDimensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#numericPrecision}.
	 * @param ctx the parse tree
	 */
	void enterNumericPrecision(PLIParser.NumericPrecisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#numericPrecision}.
	 * @param ctx the parse tree
	 */
	void exitNumericPrecision(PLIParser.NumericPrecisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#reference}.
	 * @param ctx the parse tree
	 */
	void enterReference(PLIParser.ReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#reference}.
	 * @param ctx the parse tree
	 */
	void exitReference(PLIParser.ReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtType1}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtType1(PLIParser.DoStmtType1Context ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtType1}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtType1(PLIParser.DoStmtType1Context ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtType2}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtType2(PLIParser.DoStmtType2Context ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtType2}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtType2(PLIParser.DoStmtType2Context ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtType3}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtType3(PLIParser.DoStmtType3Context ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtType3}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtType3(PLIParser.DoStmtType3Context ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtSpecification}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtSpecification(PLIParser.DoStmtSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtSpecification}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtSpecification(PLIParser.DoStmtSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtExtra}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtExtra(PLIParser.DoStmtExtraContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtExtra}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtExtra(PLIParser.DoStmtExtraContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtExtraWhile}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtExtraWhile(PLIParser.DoStmtExtraWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtExtraWhile}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtExtraWhile(PLIParser.DoStmtExtraWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtExtraUntil}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtExtraUntil(PLIParser.DoStmtExtraUntilContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtExtraUntil}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtExtraUntil(PLIParser.DoStmtExtraUntilContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(PLIParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(PLIParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#doStmtType4}.
	 * @param ctx the parse tree
	 */
	void enterDoStmtType4(PLIParser.DoStmtType4Context ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#doStmtType4}.
	 * @param ctx the parse tree
	 */
	void exitDoStmtType4(PLIParser.DoStmtType4Context ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#leaveStmt}.
	 * @param ctx the parse tree
	 */
	void enterLeaveStmt(PLIParser.LeaveStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#leaveStmt}.
	 * @param ctx the parse tree
	 */
	void exitLeaveStmt(PLIParser.LeaveStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(PLIParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(PLIParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#selectStmt}.
	 * @param ctx the parse tree
	 */
	void enterSelectStmt(PLIParser.SelectStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#selectStmt}.
	 * @param ctx the parse tree
	 */
	void exitSelectStmt(PLIParser.SelectStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#selectWhen}.
	 * @param ctx the parse tree
	 */
	void enterSelectWhen(PLIParser.SelectWhenContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#selectWhen}.
	 * @param ctx the parse tree
	 */
	void exitSelectWhen(PLIParser.SelectWhenContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#selectOtherwise}.
	 * @param ctx the parse tree
	 */
	void enterSelectOtherwise(PLIParser.SelectOtherwiseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#selectOtherwise}.
	 * @param ctx the parse tree
	 */
	void exitSelectOtherwise(PLIParser.SelectOtherwiseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#onStmt}.
	 * @param ctx the parse tree
	 */
	void enterOnStmt(PLIParser.OnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#onStmt}.
	 * @param ctx the parse tree
	 */
	void exitOnStmt(PLIParser.OnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#onCondition}.
	 * @param ctx the parse tree
	 */
	void enterOnCondition(PLIParser.OnConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#onCondition}.
	 * @param ctx the parse tree
	 */
	void exitOnCondition(PLIParser.OnConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#beginStmt}.
	 * @param ctx the parse tree
	 */
	void enterBeginStmt(PLIParser.BeginStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#beginStmt}.
	 * @param ctx the parse tree
	 */
	void exitBeginStmt(PLIParser.BeginStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#procedureStmt}.
	 * @param ctx the parse tree
	 */
	void enterProcedureStmt(PLIParser.ProcedureStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#procedureStmt}.
	 * @param ctx the parse tree
	 */
	void exitProcedureStmt(PLIParser.ProcedureStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#procedureParams}.
	 * @param ctx the parse tree
	 */
	void enterProcedureParams(PLIParser.ProcedureParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#procedureParams}.
	 * @param ctx the parse tree
	 */
	void exitProcedureParams(PLIParser.ProcedureParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#procedureParam}.
	 * @param ctx the parse tree
	 */
	void enterProcedureParam(PLIParser.ProcedureParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#procedureParam}.
	 * @param ctx the parse tree
	 */
	void exitProcedureParam(PLIParser.ProcedureParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#procedureOptions}.
	 * @param ctx the parse tree
	 */
	void enterProcedureOptions(PLIParser.ProcedureOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#procedureOptions}.
	 * @param ctx the parse tree
	 */
	void exitProcedureOptions(PLIParser.ProcedureOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#procedureOption}.
	 * @param ctx the parse tree
	 */
	void enterProcedureOption(PLIParser.ProcedureOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#procedureOption}.
	 * @param ctx the parse tree
	 */
	void exitProcedureOption(PLIParser.ProcedureOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#procedureReturns}.
	 * @param ctx the parse tree
	 */
	void enterProcedureReturns(PLIParser.ProcedureReturnsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#procedureReturns}.
	 * @param ctx the parse tree
	 */
	void exitProcedureReturns(PLIParser.ProcedureReturnsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#putStmt}.
	 * @param ctx the parse tree
	 */
	void enterPutStmt(PLIParser.PutStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#putStmt}.
	 * @param ctx the parse tree
	 */
	void exitPutStmt(PLIParser.PutStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#putOption}.
	 * @param ctx the parse tree
	 */
	void enterPutOption(PLIParser.PutOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#putOption}.
	 * @param ctx the parse tree
	 */
	void exitPutOption(PLIParser.PutOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#getStmt}.
	 * @param ctx the parse tree
	 */
	void enterGetStmt(PLIParser.GetStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#getStmt}.
	 * @param ctx the parse tree
	 */
	void exitGetStmt(PLIParser.GetStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#getOption}.
	 * @param ctx the parse tree
	 */
	void enterGetOption(PLIParser.GetOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#getOption}.
	 * @param ctx the parse tree
	 */
	void exitGetOption(PLIParser.GetOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#skipExpression}.
	 * @param ctx the parse tree
	 */
	void enterSkipExpression(PLIParser.SkipExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#skipExpression}.
	 * @param ctx the parse tree
	 */
	void exitSkipExpression(PLIParser.SkipExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#lineExpression}.
	 * @param ctx the parse tree
	 */
	void enterLineExpression(PLIParser.LineExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#lineExpression}.
	 * @param ctx the parse tree
	 */
	void exitLineExpression(PLIParser.LineExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#dataSpecification}.
	 * @param ctx the parse tree
	 */
	void enterDataSpecification(PLIParser.DataSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#dataSpecification}.
	 * @param ctx the parse tree
	 */
	void exitDataSpecification(PLIParser.DataSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#editDataList}.
	 * @param ctx the parse tree
	 */
	void enterEditDataList(PLIParser.EditDataListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#editDataList}.
	 * @param ctx the parse tree
	 */
	void exitEditDataList(PLIParser.EditDataListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#editFormatList}.
	 * @param ctx the parse tree
	 */
	void enterEditFormatList(PLIParser.EditFormatListContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#editFormatList}.
	 * @param ctx the parse tree
	 */
	void exitEditFormatList(PLIParser.EditFormatListContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#editFormat}.
	 * @param ctx the parse tree
	 */
	void enterEditFormat(PLIParser.EditFormatContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#editFormat}.
	 * @param ctx the parse tree
	 */
	void exitEditFormat(PLIParser.EditFormatContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#signalStmt}.
	 * @param ctx the parse tree
	 */
	void enterSignalStmt(PLIParser.SignalStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#signalStmt}.
	 * @param ctx the parse tree
	 */
	void exitSignalStmt(PLIParser.SignalStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#revertStmt}.
	 * @param ctx the parse tree
	 */
	void enterRevertStmt(PLIParser.RevertStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#revertStmt}.
	 * @param ctx the parse tree
	 */
	void exitRevertStmt(PLIParser.RevertStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#openFileStmt}.
	 * @param ctx the parse tree
	 */
	void enterOpenFileStmt(PLIParser.OpenFileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#openFileStmt}.
	 * @param ctx the parse tree
	 */
	void exitOpenFileStmt(PLIParser.OpenFileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#openFileOpts}.
	 * @param ctx the parse tree
	 */
	void enterOpenFileOpts(PLIParser.OpenFileOptsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#openFileOpts}.
	 * @param ctx the parse tree
	 */
	void exitOpenFileOpts(PLIParser.OpenFileOptsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#closeFileStmt}.
	 * @param ctx the parse tree
	 */
	void enterCloseFileStmt(PLIParser.CloseFileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#closeFileStmt}.
	 * @param ctx the parse tree
	 */
	void exitCloseFileStmt(PLIParser.CloseFileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#readFileStmt}.
	 * @param ctx the parse tree
	 */
	void enterReadFileStmt(PLIParser.ReadFileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#readFileStmt}.
	 * @param ctx the parse tree
	 */
	void exitReadFileStmt(PLIParser.ReadFileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#writeFileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWriteFileStmt(PLIParser.WriteFileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#writeFileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWriteFileStmt(PLIParser.WriteFileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(PLIParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(PLIParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#title}.
	 * @param ctx the parse tree
	 */
	void enterTitle(PLIParser.TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#title}.
	 * @param ctx the parse tree
	 */
	void exitTitle(PLIParser.TitleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#linesize}.
	 * @param ctx the parse tree
	 */
	void enterLinesize(PLIParser.LinesizeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#linesize}.
	 * @param ctx the parse tree
	 */
	void exitLinesize(PLIParser.LinesizeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#pagesize}.
	 * @param ctx the parse tree
	 */
	void enterPagesize(PLIParser.PagesizeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#pagesize}.
	 * @param ctx the parse tree
	 */
	void exitPagesize(PLIParser.PagesizeContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#noteStmt}.
	 * @param ctx the parse tree
	 */
	void enterNoteStmt(PLIParser.NoteStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#noteStmt}.
	 * @param ctx the parse tree
	 */
	void exitNoteStmt(PLIParser.NoteStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageStmt}.
	 * @param ctx the parse tree
	 */
	void enterPackageStmt(PLIParser.PackageStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageStmt}.
	 * @param ctx the parse tree
	 */
	void exitPackageStmt(PLIParser.PackageStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageExports}.
	 * @param ctx the parse tree
	 */
	void enterPackageExports(PLIParser.PackageExportsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageExports}.
	 * @param ctx the parse tree
	 */
	void exitPackageExports(PLIParser.PackageExportsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageExportProcedures}.
	 * @param ctx the parse tree
	 */
	void enterPackageExportProcedures(PLIParser.PackageExportProceduresContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageExportProcedures}.
	 * @param ctx the parse tree
	 */
	void exitPackageExportProcedures(PLIParser.PackageExportProceduresContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageExportProcedure}.
	 * @param ctx the parse tree
	 */
	void enterPackageExportProcedure(PLIParser.PackageExportProcedureContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageExportProcedure}.
	 * @param ctx the parse tree
	 */
	void exitPackageExportProcedure(PLIParser.PackageExportProcedureContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageReserves}.
	 * @param ctx the parse tree
	 */
	void enterPackageReserves(PLIParser.PackageReservesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageReserves}.
	 * @param ctx the parse tree
	 */
	void exitPackageReserves(PLIParser.PackageReservesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageReservedVariables}.
	 * @param ctx the parse tree
	 */
	void enterPackageReservedVariables(PLIParser.PackageReservedVariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageReservedVariables}.
	 * @param ctx the parse tree
	 */
	void exitPackageReservedVariables(PLIParser.PackageReservedVariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageReservedVariable}.
	 * @param ctx the parse tree
	 */
	void enterPackageReservedVariable(PLIParser.PackageReservedVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageReservedVariable}.
	 * @param ctx the parse tree
	 */
	void exitPackageReservedVariable(PLIParser.PackageReservedVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageReservedExternal}.
	 * @param ctx the parse tree
	 */
	void enterPackageReservedExternal(PLIParser.PackageReservedExternalContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageReservedExternal}.
	 * @param ctx the parse tree
	 */
	void exitPackageReservedExternal(PLIParser.PackageReservedExternalContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageOptions}.
	 * @param ctx the parse tree
	 */
	void enterPackageOptions(PLIParser.PackageOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageOptions}.
	 * @param ctx the parse tree
	 */
	void exitPackageOptions(PLIParser.PackageOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#packageOption}.
	 * @param ctx the parse tree
	 */
	void enterPackageOption(PLIParser.PackageOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#packageOption}.
	 * @param ctx the parse tree
	 */
	void exitPackageOption(PLIParser.PackageOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#gotoStmt}.
	 * @param ctx the parse tree
	 */
	void enterGotoStmt(PLIParser.GotoStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#gotoStmt}.
	 * @param ctx the parse tree
	 */
	void exitGotoStmt(PLIParser.GotoStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#stopStmt}.
	 * @param ctx the parse tree
	 */
	void enterStopStmt(PLIParser.StopStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#stopStmt}.
	 * @param ctx the parse tree
	 */
	void exitStopStmt(PLIParser.StopStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(PLIParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(PLIParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(PLIParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(PLIParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(PLIParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(PLIParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#repetitionFactor}.
	 * @param ctx the parse tree
	 */
	void enterRepetitionFactor(PLIParser.RepetitionFactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#repetitionFactor}.
	 * @param ctx the parse tree
	 */
	void exitRepetitionFactor(PLIParser.RepetitionFactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(PLIParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(PLIParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleIdentifier(PLIParser.SimpleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleIdentifier(PLIParser.SimpleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMember(PLIParser.MemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMember(PLIParser.MemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#subscript}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(PLIParser.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#subscript}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(PLIParser.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link PLIParser#picture}.
	 * @param ctx the parse tree
	 */
	void enterPicture(PLIParser.PictureContext ctx);
	/**
	 * Exit a parse tree produced by {@link PLIParser#picture}.
	 * @param ctx the parse tree
	 */
	void exitPicture(PLIParser.PictureContext ctx);
}