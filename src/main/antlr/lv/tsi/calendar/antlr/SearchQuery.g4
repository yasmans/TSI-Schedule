/*  Search Query Grammar
  Search query should support these features:
    - room keyword for filtering by room
    - teacher keyword for filtering by teacer
    - group keyword for filtering by group
    - '-' symbol for negation for excluding results by criteria

    P.S By convention grammar rules should start with lowercase letter and parser rule with capital.
*/

grammar SearchQuery;

exclude
    : '-' group
    | '-' teacher
    | '-' room
    | '-' keyword
    ;

group
    : 'group:' keyword
    | 'g:' keyword
    ;

teacher
    : 'teacher:' keyword
    | 't:' keyword
    ;

room
    : 'room:' keyword
    | 'r:' keyword
    ;

keyword
    : '\'' STR+ '\''
    | '"' STR+ '"'
    | STR
    ;

STR : [w]+ ;
WS : [ \t\r\n]+ -> skip ;