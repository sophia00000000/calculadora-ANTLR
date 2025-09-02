grammar LabeledExpr; // rename to distinguish from Expr.g4

prog:   stat+ ;

stat:   expr NEWLINE                # printExpr
    |   ID '=' expr NEWLINE         # assign
    |   'mode' 'grados' NEWLINE     # setModeGrados
    |   'mode' 'radianes' NEWLINE   # setModeRadianes
    |   NEWLINE                     # blank
    ;

expr:   expr '!'                    # Factorial
    |   func '(' expr ')'           # FuncCall
    |   '-' expr                    # Negate
    |   expr '^' expr               # Power
    |   expr op=('*'|'/') expr      # MulDiv
    |   expr op=('+'|'-') expr      # AddSub
    |   NUMBER                      # Number
    |   ID                          # id
    |   '(' expr ')'                # parens
    ;

func:   'sin' 
    |   'cos' 
    |   'tan'
    |   'sqrt' 
    |   'ln' 
    |   'log'
    ;


// TOKENS 
MUL :   '*' ; // assigns token name to '*' used above in grammar
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
ID  :   [a-zA-Z]+ ;      // match identifiers
NUMBER :    [0-9]+ ('.' [0-9]+)? ;       // match decimales
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace