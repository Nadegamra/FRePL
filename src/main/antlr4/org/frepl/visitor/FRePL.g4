grammar FRePL;

program
: statement+ EOF
;

statement
: systemFunction
| declaration
| assignment
| conditionalStatement
| whileLoop
| returnStatement
| functionDeclaration
| functionCall
| block
;

returnStatement: 'return' expression? ;

functionDeclaration: TYPE IDENTIFIER'(' parametersList? ')' functionBody;
parametersList: functionParameter (','functionParameter)* ;
functionParameter: TYPE IDENTIFIER;
functionBody: '{' statement+'}' ;


conditionalStatement
: ifStatement elseIfStatement* elseStatement?;

ifStatement: 'if' '(' expression ')' block;
elseIfStatement: 'else' 'if' '(' expression ')' block;
elseStatement: 'else' block;

whileLoop: 'while' '(' expression ')' block;
block: '{' statement* '}';

systemFunction
: 'Print(' expression ')'                       #printFunctionCall
| 'Print()'                                     #printNewLine
| 'Read()'                                      #readFunctionCall
| 'Save(' STRING ')'                            #saveFunctionCall
| 'Load(' STRING ')'                            #loadFunctionCall
| 'int('    expression')'                       #parseInt
| 'float('  expression')'                       #parseFloat
| 'bool('   expression')'                       #parseBool
| 'char('   expression')'                       #parseChar
| 'string(' expression')'                       #parseString
| IDENTIFIER'.add('expression')'                #arrayAddElement
| IDENTIFIER'.remove('expression')'             #arrayRemoveAtIndex
| IDENTIFIER'['expression']' '=' expression     #arraySetElement
;

declaration
: TYPE IDENTIFIER                    #declarationWithoutValue
| TYPE IDENTIFIER '=' expression     #declarationWithValue
|'var' IDENTIFIER '=' expression     #declarationImplicitType
;

assignment : IDENTIFIER '=' expression;

expression
: constant                          #constantExpression
| constantArr                       #constantArrayExpression
| systemFunction                    #functionReturnExpression
| ioResult                          #fileExpression
| IDENTIFIER                        #identifierExpression
| '(' expression ')'                #parenthesisExpression
| unNegOp expression                #unaryNegationExpression
| expression binBoolOp expression   #binaryBooleanExpression
| expression binPowOp expression    #binaryPowerExpression
| expression binMultOp expression   #binaryMultExpression
| expression binAddOp expression    #binaryAddExpression
| expression binCatOp expression    #binaryConcatExpression
| expression binCompOp expression   #binaryComparisonExpression
| functionCall                      #functionCallExpression
| IDENTIFIER'['expression']'        #arrayGetElement
| IDENTIFIER'.length()'             #arrayGetLength
;

functionCall: IDENTIFIER'('functionArgumentList?')';
functionArgumentList: expression (','expression)* ;

ioResult
: 'fetch' STRING 'line' INT 'field' INT 'delimedBy' STRING      #fileVar
| 'fetch' STRING 'line' INT                                     #fileLine
| 'fetch' STRING                                                #fileText
;

unNegOp: '!';
binBoolOp: '&&' | '||';
binPowOp: '**';
binMultOp: '*' | '/' | '%';
binAddOp: '+' | '-';
binCatOp: '.';
binCompOp: '<=' | '>' | '!=' | '==' | '<' | '>=' | '<=>';

constantArr
: '['(INT)+']'
| '['(FLOAT)+']'
| '['(BOOLEAN)+']'
| '['(STRING)+']'
| '['(CHAR)+']'
;

constant: INT | FLOAT | BOOLEAN | STRING | CHAR ;

// DATA TYPES
INT: [0-9]+ ;
FLOAT: [0-9]+ '.' [0-9]+ ;
BOOLEAN: 'false' | 'true' ;
STRING : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["] ;
CHAR: ['] ( ~["\r\n\\] | '\\' ~[\r\n] ) ['];

TYPE: 'void' | 'int' | 'bool' | 'char' | 'string' | 'float'
    | 'int[]' | 'bool[]' | 'char[]' | 'string[]' | 'float[]' ;
IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]* ;

//SKIPPED
COMMENT: ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;
WHITESPACE: [ \t\r\n] -> skip;