grammar FRePL;

program
: statement+ EOF
;

statement
: systemFunction
//| declaration
;

systemFunction
: 'PRINT(' expression ')'   #printFunctionCall
| 'READ()'                  #readFunctionCall
;

//declaration
//: TYPE IDENTIFIER
//| TYPE IDENTIFIER '=' expression
//;


expression: constant;

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