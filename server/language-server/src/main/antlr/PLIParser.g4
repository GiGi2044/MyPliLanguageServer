parser grammar PLIParser;

options {   tokenVocab = PLILexer; }

program                     : (stmt)* EOF
                            ;

stmt                        : directive
                            | label? statement
                            | label
                            ;

preconditions               : (OPEN_PAREN conditions (COMMA conditions)* CLOSE_PAREN)
                            ;
comment                     : COMMENT
                            ;

inject                      : INJECT
                            ;

label                       : IDENTIFIER COLON?
                            | MAIN COLON?
                            | INITIAL_ COLON?
                            | LOOP COLON?
                            | OPEN COLON?
                            | CLOSE COLON?
                            | FETCH COLON?
                            | UPDATE COLON?
                            ;

conditions                  :  CONDITION OPEN_PAREN identifier CLOSE_PAREN
                            |   CHECK
                               //| CHECK '(' varnamerefcommalist ')'
                            | CONVERSION
                            | FIXEDOVERFLOW
                            | INVALIDOP
                            | OVERFLOW
                            | SIZE
                            | STRINGRANGE
                            | STRINGSIZE
                            | SUBSCRIPTRANGE
                            | UNDERFLOW_
                            | UNDEFINEDFILE
                            | ZERODIVIDE
                            | NOCHECK
                            | NOCONVERSION
                            | NOFIXEDOVERFLOW
                            | NOINVALIDOP
                            | NOOVERFLOW
                            | NOSIZE
                            | NOSUBSCRIPTRANGE
                            | NOSTRINGRANGE
                            | NOSTRINGSIZE
                            | NOUNDERFLOW
                            | NOZERODIVIDE
                            | ENDFILE
                            | KEY
                            | ERROR
                            | IDENTIFIER
                            ;

directive                   : include
                            | PERCENT PRINT
                            | PERCENT NOPRINT
                            ;

include                     : INCLUDE includedMember (COMMA includedMember)* SEMICOLON
                            ;

includedMember              : identifier (DOT identifier)*
                            | STRING_LITERAL
                            ;

statement                   : packageStmt
                            | procedureStmt
                            | beginStmt
                            | entryStmt
                            | declareStmt
                            | assignStmt
                            | defineStmt
                            | callStmt
                            | returnStmt
                            | comment
                            | doStmtType1
                            | doStmtType2
                            | doStmtType3
                            | doStmtType4
                            | leaveStmt
                            | ifStmt
                            | selectStmt
                            | onStmt
                            | signalStmt
                            | revertStmt
                            | putStmt
                            | getStmt
                            | openFileStmt
                            | closeFileStmt
                            | readFileStmt
                            | writeFileStmt
                            | gotoStmt
                            | stopStmt
                            | noteStmt
                            | nopStmt
                            | execSQL
                            | execCICS
                            | defRange
                            ;
defRange                    : DEFAULT defExpression (COMMA defExpression)* attributes SEMICOLON
                            ;
defExpression               : RANGE OPEN_PAREN STAR CLOSE_PAREN
                            | OPEN_PAREN CLOSE_PAREN
                            ;

execSQL                     : EXEC SQL SQL_BODY
                            ;

execCICS                    : EXEC CICS CICS_BODY
                            ;

nopStmt                     : SEMICOLON;

assignStmt                  : identifier (COMMA identifier)* EQUAL expression assignByName? SEMICOLON
                            | identifier (COMMA identifier)* PLUSEQUAL expression assignByName? SEMICOLON
                            | identifier (COMMA identifier)* MINUSEQUAL expression assignByName? SEMICOLON
                            | identifier (COMMA identifier)* STAREQUAL  expression assignByName? SEMICOLON
                            | identifier (COMMA identifier)* SLASHEQUAL expression assignByName? SEMICOLON
                            ;

assignByName                : (COMMA BY NAME)
                            ;

defineStmt                  : DEFINE ALIAS identifier attributes* SEMICOLON
                            | DEFINE ORDINAL identifier OPEN_PAREN  ordinalValues CLOSE_PAREN  SEMICOLON
                            ;

ordinalValues               : identifier (COMMA identifier)*
                            ;

declareStmt                 : DECLARE declaration (COMMA declaration)*   SEMICOLON  inject?
                            | DECLARE OPEN_PAREN declaration (COMMA declaration)*  CLOSE_PAREN  SEMICOLON
                            ;

declaration                 : level? name (attributes)* UNION?
                            | name GENERIC  OPEN_PAREN genericRefs CLOSE_PAREN
                            | identifier
                            ;
genericRefs                 : genericRef (COMMA genericRef)*
                            ;
genericRef                  : name  WHEN  OPEN_PAREN genericRefData? (COMMA genericRefData)* CLOSE_PAREN
                            ;
genericRefData              : data
                            | STAR
                            ;

customType                  :   IDENTIFIER
                            ;

level                       : INTEGER_LITERAL
                            ;

name                        : identifier
                            | OPEN_PAREN factoredNames CLOSE_PAREN
                            | STAR
                            ;

factoredNames               : identifier (COMMA identifier)*
                            ;

attributes                  : data
                            | entry
                            | alignment
                            | scope
                            | storage
                            //| complementary
                            | constant
                            | initialization
                            ;


data                        : BINARY        (OPEN_PAREN precision  CLOSE_PAREN)?
                            | DECIMAL       (OPEN_PAREN precision  CLOSE_PAREN)?
                            | FLOAT         (OPEN_PAREN precision  CLOSE_PAREN)?
                            | (VARYING | VARYINGZ)? (CHARACTER | VARCHAR)  (OPEN_PAREN expression CLOSE_PAREN)? (VARYING | VARYINGZ)?
                            | (VARYING | VARYINGZ)? (CHARACTER | VARCHAR)  (OPEN_PAREN STAR CLOSE_PAREN)? (VARYING | VARYINGZ)?
                            | FIXED         (OPEN_PAREN precision  CLOSE_PAREN)?
                            | PICTURE       expression
                            | POINTER
                            | BIT           (OPEN_PAREN expression CLOSE_PAREN)? (VARYING? | VARYINGZ?)
                            | BUILTIN
                            | BASED         (OPEN_PAREN expression CLOSE_PAREN)
                            | CONNECTED
                            | FILE_
                            | RECORD
                            | PRINT
                            | SEQUENTIAL
                            | VARIABLE
                            | INPUT
                            | STREAM
                            | OUTPUT
                            | UPDATE
                            | DIRECT
                            | BUFFERED
                            | UNBUFFERED
                            | SYSIN
                            | SYSOUT
                            | SYSERR
                            | KEYED
                            | LIKE          like=identifier
                            | LABEL
                            | CONDITION
                            | UNSIGNED
                            | ON
                            | BYADDR
                            | BYVALUE
                            | TYPE customType
                            //TODO add more types
                            ;

entry                       : ENTRY         (OPEN_PAREN entryParam (COMMA entryParam)* CLOSE_PAREN)? // see p.122
                                            (RETURNS  OPEN_PAREN procedureReturns CLOSE_PAREN)?
                                            (OPTIONS  OPEN_PAREN procedureOptions CLOSE_PAREN)?
                                            (EXTERNAL OPTIONS? OPEN_PAREN externalName* CLOSE_PAREN)?
                            ;


entryParam                  : (attributes)*
                            | STAR
                            | OPEN_PAREN STAR CLOSE_PAREN (attributes)*
                            ;

precision                   : INTEGER_LITERAL (COMMA decimals)?
                            ;

decimals                    : INTEGER_LITERAL
                            ;


alignment                   : ALIGNED
                            | UNALIGNED
                            ;

scope                       : INTERNAL
                            | EXTERNAL
                            | EXTERNAL OPEN_PAREN externalName CLOSE_PAREN
                            | ENVIRONMENT OPEN_PAREN VSAM? externalName* CLOSE_PAREN
                            | OPTIONS OPEN_PAREN VSAM? externalName (COMMA externalName)* CLOSE_PAREN
                            | RESERVED
                            ;
externalName                : STRING_LITERAL
                            | GENKEY
                            | ASSEMBLER
                            | ASM
                            | INTER
                            | RETCODE
                            | FB
                            | CTLASA
                            | identifier
                            ;

storage                     : STATIC
                            | AUTOMATIC
                            | BASED (OPEN_PAREN locator CLOSE_PAREN)?
                            | DEFINED locator (POSITION OPEN_PAREN expression CLOSE_PAREN)?
                            | CONTROLLED
                            ;

locator                     : expression
                            ;


constant                    : VALUE OPEN_PAREN expression CLOSE_PAREN
                            ;

initialization              : INITIAL_ OPEN_PAREN initValue (COMMA initValue)* CLOSE_PAREN
                            | INITIAL_ callStmt
                            | INITIAL_ TO OPEN_PAREN initSpec CLOSE_PAREN OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN
                            ;

initSpec                    : VARYING
                            | VARYINGZ
                            | NONVARYING
                            ;

initValue                   : repetitionFactor? expression
                            ;

callStmt                    : CALL callTarget (OPEN_PAREN (expression)* CLOSE_PAREN)? SEMICOLON
                            | CALL callTarget (OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN)? SEMICOLON
                            ;
callTarget                  : IDENTIFIER
                            | INITIAL_
                            ;

returnStmt                  : RETURN (OPEN_PAREN expression CLOSE_PAREN)? SEMICOLON
                            ;

entryStmt                   : ENTRY /* TODO params and return value */ SEMICOLON
                            ;


arrayDimensions             : arrayDimension  (COMMA arrayDimension)*
                            ;

arrayDimension              : expression
                            | STAR
                            ;


numericPrecision            : OPEN_PAREN precision CLOSE_PAREN
                            ;


reference                   : IDENTIFIER
                            ;

doStmtType1                 : DO SEMICOLON (stmt)* END SEMICOLON
                            ;

doStmtType2                 : DO type=WHILE OPEN_PAREN dwexp1=expression CLOSE_PAREN (UNTIL OPEN_PAREN dwexp2=expression CLOSE_PAREN)? SEMICOLON (stmt)* END SEMICOLON
                            | DO type=UNTIL OPEN_PAREN duexp1=expression CLOSE_PAREN (WHILE OPEN_PAREN duexp2=expression CLOSE_PAREN)? SEMICOLON (stmt)* END SEMICOLON
                            ;

doStmtType3                 : DO identifier EQUAL doStmtSpecification (COMMA doStmtSpecification)* SEMICOLON (stmt)* END SEMICOLON
                            ;

doStmtSpecification         : exp1=expression doStmtExtra?
                            | exp1=expression TO expTo=expression (BY expBy=expression )? doStmtExtra?
                            | exp1=expression BY expBy=expression (TO expTo=expression )? doStmtExtra?
                            | exp1=expression UPTHRU expUp=expression doStmtExtra?
                            | exp1=expression DOWNTHRU expDown=expression doStmtExtra?
                            | exp1=expression REPEAT expRepeat=expression doStmtExtra?
                            ;

doStmtExtra                 : doStmtExtraWhile
                            | doStmtExtraUntil
                            ;

doStmtExtraWhile            : WHILE OPEN_PAREN expWhile=expression CLOSE_PAREN doStmtExtraUntil?
                            ;

doStmtExtraUntil            : UNTIL OPEN_PAREN expUntil=expression CLOSE_PAREN doStmtExtraWhile?
                            ;

expressionList              : (COMMA expression)*
                            ;

doStmtType4                 : DO (LOOP | FOREVER) SEMICOLON (stmt)* END SEMICOLON
                            ;

leaveStmt                   : LEAVE label?;


ifStmt                      : IF expression THEN stmt (ELSE  stmt)?
                            ;

selectStmt                  : SELECT (OPEN_PAREN expression CLOSE_PAREN)? SEMICOLON
                              (selectWhen)*
                               selectOtherwise?
                              END SEMICOLON
                            ;

selectWhen                  : WHEN OPEN_PAREN expression  (COMMA expression)* CLOSE_PAREN (stmt)
                            ;

selectOtherwise             : OTHERWISE (stmt)
                            | OTHERWISE SEMICOLON
                            ;

onStmt                      : ON onCondition SNAP? stmt
                            | ON onCondition SNAP? beginStmt
                            | ON onCondition SNAP? SYSTEM SEMICOLON
                            ;

onCondition                 : condition=conditions (OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN)?
                            | CONDITION OPEN_PAREN expression CLOSE_PAREN COMMA (onCondition)*
                            ;

beginStmt                   : BEGIN_ SEMICOLON (stmt)* END identifier? SEMICOLON
                            ;

procedureStmt               : PROCEDURE (OPEN_PAREN procedureParams? CLOSE_PAREN)?
                                        REORDER? RECURSIVE? (OPTIONS OPEN_PAREN procedureOptions CLOSE_PAREN)?
                                        (RETURNS OPEN_PAREN procedureReturns CLOSE_PAREN)? REORDER? SEMICOLON
                              (stmt)*
                              END (identifier)? SEMICOLON
                            ;

procedureParams             : procedureParam  (COMMA procedureParam)*
                            ;

procedureParam              : identifier
                            ;

procedureOptions            : procedureOption  (COMMA? procedureOption)*
                            ;


procedureOption             : MAIN
                            | REENTRANT
                            | RECURSIVE
                            | COBOL
                            | ASM
                            | INTER
                            | NOEXECOPS
                            | BYVALUE
                            ;

procedureReturns            : (attributes)*
                            | TYPE identifier
                            ;

putStmt                     : PUT (putOption)* SEMICOLON
                            ;

putOption                   : FILE_ OPEN_PAREN expression CLOSE_PAREN
                            | PAGE
                            | SKIP_
                            | SKIP_ OPEN_PAREN skipExpression CLOSE_PAREN
                            | LINE OPEN_PAREN lineExpression CLOSE_PAREN
                            | STRING OPEN_PAREN expression CLOSE_PAREN
                            //| LIST OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN // TODO to be verified
                            //| DATA OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN // TODO to be verified
                            | dataSpecification
                            ;

getStmt                     : GET  (getOption)* SEMICOLON
                            ;

getOption                   : FILE_ OPEN_PAREN expression CLOSE_PAREN
                            | STRING OPEN_PAREN expression CLOSE_PAREN
                            | COPY OPEN_PAREN reference CLOSE_PAREN
                            | SKIP_
                            | SKIP_ OPEN_PAREN skipExpression CLOSE_PAREN
                            | dataSpecification
                            ;

skipExpression              :   expression
                            ;
lineExpression              :   expression
                            ;

dataSpecification           : LIST OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN
                            | DATA OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN
                            | EDIT editDataList editFormatList
                            ;
editDataList                : OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN
                            ;
editFormatList              : OPEN_PAREN editFormat (COMMA editFormat)* CLOSE_PAREN
                            ;

editFormat                  : expression
                            ;


signalStmt                  : SIGNAL conditions SEMICOLON
                            ;

revertStmt                  : REVERT conditions SEMICOLON
                            ;

openFileStmt                : OPEN  file (COMMA file)* (openFileOpts)* title? linesize? pagesize? SEMICOLON
                            ;
openFileOpts                : STREAM
                            | RECORD
                            | INPUT
                            | OUTPUT
                            | UPDATE
                            | SEQUENTIAL
                            | DIRECT
                            | BUFFERED
                            | UNBUFFERED
                            | KEYED
                            | PRINT
                            ;

closeFileStmt               : CLOSE file (COMMA file)*  SEMICOLON
                            ;

readFileStmt                : READ file INTO OPEN_PAREN expression CLOSE_PAREN SEMICOLON
                            | READ file IGNORE OPEN_PAREN expression CLOSE_PAREN SEMICOLON
                            | READ file SET OPEN_PAREN expression CLOSE_PAREN SEMICOLON
                            ;

writeFileStmt               : WRITE file FROM OPEN_PAREN expression CLOSE_PAREN (KEYFROM OPEN_PAREN expression CLOSE_PAREN)? SEMICOLON
                            ;

file                        : FILE_ OPEN_PAREN identifier CLOSE_PAREN
                            ;

title                       : TITLE OPEN_PAREN expression CLOSE_PAREN
                            ;

linesize                    : LINESIZE OPEN_PAREN expression CLOSE_PAREN
                            ;

pagesize                    : PAGESIZE OPEN_PAREN expression CLOSE_PAREN
                            ;

noteStmt                    : NOTE OPEN_PAREN expression COMMA expression CLOSE_PAREN
                            ;
packageStmt                 : PACKAGE SEMICOLON
                              (stmt)*
                              (END (identifier)? SEMICOLON)?
                            | PACKAGE (packageExports)? (packageReserves)? (packageOptions)? SEMICOLON
                              (stmt)*
                              (END (identifier)? SEMICOLON)?
                            ;

packageExports              : EXPORTS OPEN_PAREN STAR CLOSE_PAREN
                            | EXPORTS OPEN_PAREN packageExportProcedures CLOSE_PAREN
                            ;

packageExportProcedures     : packageExportProcedure (COMMA packageExportProcedure)*
                            ;

packageExportProcedure      : IDENTIFIER
                            ;

packageReserves             : RESERVES OPEN_PAREN STAR CLOSE_PAREN
                            | RESERVES OPEN_PAREN packageReservedVariables CLOSE_PAREN
                            ;
packageReservedVariables    : packageReservedVariable (COMMA packageReservedVariable)*
                            ;
packageReservedVariable     : IDENTIFIER (packageReservedExternal)?
                            ;
packageReservedExternal     : EXTERNAL OPEN_PAREN IDENTIFIER CLOSE_PAREN
                            ;

packageOptions              : OPTIONS OPEN_PAREN packageOption  (COMMA packageOption)* CLOSE_PAREN
                            ;

packageOption               : NOCHARGRAPHIC
                            | CHARGRAPHIC
                            | ORDER
                            | REORDER
                            | REENTRANT
                            ;
gotoStmt                    : GO TO label SEMICOLON
                            | GOTO label SEMICOLON
                            ;

stopStmt                    : STOP
                            ;


expression                  : literal
                            | identifier
                            | (NOT | PLUS | MINUS) expression
                            | expression STARSTAR expression
                            | expression (STAR | SLASH | PERCENT) expression
                            | expression (PLUS | MINUS | CONCAT) expression
                            | expression comparison expression
                            | expression EQUAL expression
                            | expression (AND | OR) expression
                            | CALL (expression)*
                            | OPEN_PAREN expression CLOSE_PAREN
                            ;

comparison                  :   (NE | GT | LT | GE | LE )
                            ;

literal                     : repetitionFactor? INTEGER_LITERAL
                            | DECIMAL_LITERAL
                            | FLOAT_LITERAL
                            | HEX_LITERAL
                            | repetitionFactor?  STRING_LITERAL
                            | repetitionFactor?  BIT_LITERAL
                            //| P_FORMAT
                            ;

repetitionFactor            : (OPEN_PAREN repeater=expression CLOSE_PAREN)
                            ;

identifier                  : simpleIdentifier
                            | identifier DOT member
                            | identifier OPEN_PAREN subscript? (COMMA subscript)* CLOSE_PAREN
                            | identifier picture
                            ;

simpleIdentifier            : IDENTIFIER
                            /* Keywords */
                            | (MAIN
                            | INITIAL_
                            | TITLE
                            | ADDR
                            | FIXED
                            | COPY
                            | FROM
                            | REPLY
                            | CONDITION
                            | KEY
                            | DATE
                            | PAGE
                            | DATA
                            | OPEN
                            | FETCH
                            | CLOSE
                            | IN
                            | SYSTEM
                            | NAME
                            | VALUE
                            | OFFSET
                            | PAGESIZE
                            | LINESIZE
                            | RESERVED
                            | NOTE
                            | STRING
                            | SYSIN
                            | SIZE
                            | RECORD
                            | OPTIONS
                            | FORMAT
                            | PRIORITY
                            | SUPPORT
                            | RANGE
                            | NORMAL
                            | SYSNULL
                            | DEFAULT
                            | REPEAT
                            | CHARACTER
                            | VARCHAR
                            | STORAGE
                            | PARAMETER
                            | DIMENSION
                            | DECLARE
                            | ORDER
                            | UPDATE
                            | ON
                            | POSITION
                            | DIRECT
                            | OUTPUT
                            | ERROR
                            | RECSIZE
                            | BUFFERS
                            | TOTAL
                            | POINTER
                            | FB)
                            ;

member                      : identifier
                            ;

subscript                   : expression
                            | STAR
                            ;

picture                     : STRING_LITERAL
                            ;
