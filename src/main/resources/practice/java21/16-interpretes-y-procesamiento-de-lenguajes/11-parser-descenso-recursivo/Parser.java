// Tema:
// Descenso recursivo: expresión = término {(+|-) término}; término = factor {(*|/) factor}; factor = NUM | ID | '(' expresión ')'. Los bucles asocian a izquierda y las llamadas reflejan precedencia. El AST conserva la estructura, no evalúa.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Token.java Lexer.java Nodo.java Parser.java MainParser.java
// Ejecutar: java MainParser

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int i;
    public Parser(String fuente) { tokens = Lexer.analizar(fuente); }
    private Token actual() { return tokens.get(i); }
    private boolean es(String tipo) { return actual().tipo().equals(tipo); }

    public Nodo analizar() {
        Nodo n = expresion();
        if (!es("EOF")) throw new IllegalArgumentException("Token sobrante");
        return n;
    }
    private Nodo expresion() {
        Nodo n = termino();
        while (es("+") || es("-")) {
            String op = tokens.get(i++).texto();
            n = new Nodo(op, n, termino());
        }
        return n;
    }
    private Nodo termino() {
        Nodo n = factor();
        while (es("*") || es("/")) {
            String op = tokens.get(i++).texto();
            n = new Nodo(op, n, factor());
        }
        return n;
    }
    private Nodo factor() {
        if (es("NUM") || es("ID")) return Nodo.hoja(tokens.get(i++).texto());
        if (es("(")) {
            i++;
            Nodo n = expresion();
            if (!es(")")) throw new IllegalArgumentException("Falta )");
            i++;
            return n;
        }
        throw new IllegalArgumentException("Factor esperado en " + actual().posicion());
    }
}

// Salida esperada:
// (+ 2 (* 3 4))
// (* (+ 2 3) 4)
// (- (- 9 3) 2)
// Sintaxis invalida
