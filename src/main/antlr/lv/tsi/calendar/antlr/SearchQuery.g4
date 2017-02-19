grammar SearchQuery;

// ------------------- Parser Grammar -----------------

searchQuery : term+ | EOF ;

term : negSearchTerm | searchTerm ;

negSearchTerm
    : '-' group
    | '-' teacher
    | '-' room
    | '-' SEARCH_TERM
    ;

searchTerm
    : group
    | teacher
    | room
    | SEARCH_TERM
    ;

group
    : 'group:' SEARCH_TERM
    | 'g:' SEARCH_TERM
    ;

teacher
    : 'teacher:' SEARCH_TERM
    | 't:' SEARCH_TERM
    ;

room
    : 'room:' SEARCH_TERM
    | 'r:' SEARCH_TERM
    ;

// ------------------- Lexer Grammar -----------------

// Unicode letter or digit
fragment LetterOrDigitWithSpace
    : [a-zA-Z0-9_ ]
    | ~[\u0000-\u007F\uD800-\uDBFF]
    | [\uD800-\uDBFF] [\uDC00-\uDFFF]
    ;

fragment LetterOrDigit
    : [a-zA-Z0-9_]
    | ~[\u0000-\u007F\uD800-\uDBFF]
    | [\uD800-\uDBFF] [\uDC00-\uDFFF]
    ;

SEARCH_TERM
    : '"' LetterOrDigitWithSpace+ '"'
    | '\'' LetterOrDigitWithSpace+ '\''
    | LetterOrDigit+
    ;

// Ignore whitespace charachters within search term
WS : [ \t\r\n]+ -> skip;