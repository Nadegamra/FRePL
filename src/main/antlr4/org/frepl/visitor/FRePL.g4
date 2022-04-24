grammar FRePL;

program
: statement+ EOF
;

statement
: systemFunction
| declaration
| assignment
;

systemFunction
: 'PRINT(' expression ')'   #printFunctionCall
| 'PRINT()'                 #printNewLine
| 'READ()'                  #readFunctionCall
;

declaration
: TYPE IDENTIFIER                    #declarationWithoutValue
| TYPE IDENTIFIER '=' expression     #declarationWithValue
|'var' IDENTIFIER '=' expression     #declarationImplicitType
;

assignment : IDENTIFIER '=' expression;

expression
: constant                          #constantExpression
| IDENTIFIER                        #identifierExpression
| '(' expression ')'                #parenthesisExpression
| unNegOp expression                #unaryNegationExpression
| expression binBoolOp expression   #binaryBooleanExpression
| expression binMultOp expression   #binaryMultExpression
| expression binAddOp expression    #binaryAddExpression
| expression binCatOp expression    #binaryConcatExpression
;

unNegOp: '!';
binBoolOp: '&&' | '||';
binMultOp: '*' | '/' | '%';
binAddOp: '+' | '-';
binCatOp: '.';

constant: INT | FLOAT | BOOLEAN | STRING | CHAR ;

// DATA TYPES
INT: [0-9]+ ;
FLOAT: [0-9]+ '.' [0-9]+ ;
BOOLEAN: 'false' | 'true' ;
STRING : ["] ( ~["\r\n\\] | '\\' ~[\r\n] )* ["] ;
CHAR: ['] ( ~["\r\n\\] | '\\' ~[\r\n] ) ['];

TYPE: 'int' | 'bool' | 'char' | 'string' | 'float' ;
IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]* ;


//SKIPPED
COMMENT: ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;
WHITESPACE: [ \t\r\n] -> skip;