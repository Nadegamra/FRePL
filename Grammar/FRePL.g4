grammar FRePL;

program: constant+ EOF;

constant: INT | FLOAT | BOOLEAN;

INT: [0-9]+;

FLOAT: [0-9]+ '.' [0-9]+;

BOOLEAN: 'false' | 'true';

COMMENT: ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;

WHITESPACE: [ \t\r\n] -> skip; 