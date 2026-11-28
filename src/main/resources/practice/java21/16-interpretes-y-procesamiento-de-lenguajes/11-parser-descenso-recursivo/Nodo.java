// Tema:
// Descenso recursivo: expresión = término {(+|-) término}; término = factor {(*|/) factor}; factor = NUM | ID | '(' expresión ')'. Los bucles asocian a izquierda y las llamadas reflejan precedencia. El AST conserva la estructura, no evalúa.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Token.java Lexer.java Nodo.java Parser.java MainParser.java
// Ejecutar: java MainParser

public record Nodo(String texto, Nodo izquierda, Nodo derecha) {
    public static Nodo hoja(String texto) { return new Nodo(texto, null, null); }
    public String prefijo() {
        if (izquierda == null) return texto;
        return "(" + texto + " " + izquierda.prefijo() + " " + derecha.prefijo() + ")";
    }
}

// Salida esperada:
// (+ 2 (* 3 4))
// (* (+ 2 3) 4)
// (- (- 9 3) 2)
// Sintaxis invalida
