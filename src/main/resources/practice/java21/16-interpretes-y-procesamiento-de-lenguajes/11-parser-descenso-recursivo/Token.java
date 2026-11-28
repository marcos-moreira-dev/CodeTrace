// Tema:
// Descenso recursivo: expresión = término {(+|-) término}; término = factor {(*|/) factor}; factor = NUM | ID | '(' expresión ')'. Los bucles asocian a izquierda y las llamadas reflejan precedencia. El AST conserva la estructura, no evalúa.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Token.java Lexer.java Nodo.java Parser.java MainParser.java
// Ejecutar: java MainParser

public record Token(String tipo, String texto, int posicion) { }

// Salida esperada:
// (+ 2 (* 3 4))
// (* (+ 2 3) 4)
// (- (- 9 3) 2)
// Sintaxis invalida
