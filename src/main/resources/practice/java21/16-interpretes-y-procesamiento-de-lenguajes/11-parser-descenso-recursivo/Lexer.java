// Tema:
// Descenso recursivo: expresión = término {(+|-) término}; término = factor {(*|/) factor}; factor = NUM | ID | '(' expresión ')'. Los bucles asocian a izquierda y las llamadas reflejan precedencia. El AST conserva la estructura, no evalúa.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Token.java Lexer.java Nodo.java Parser.java MainParser.java
// Ejecutar: java MainParser

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static List<Token> analizar(String fuente) {
        if (fuente.length() > 256) throw new IllegalArgumentException("Fuente larga");
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < fuente.length()) {
            char c = fuente.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            int inicio = i++;
            String tipo;
            if (c >= '0' && c <= '9') {
                tipo = "NUM";
                while (i < fuente.length() && fuente.charAt(i) >= '0' && fuente.charAt(i) <= '9') i++;
            } else if (Character.isLetter(c) || c == '_') {
                tipo = "ID";
                while (i < fuente.length() && (Character.isLetterOrDigit(fuente.charAt(i)) || fuente.charAt(i) == '_')) i++;
            } else if ("+-*/()".indexOf(c) >= 0) {
                tipo = String.valueOf(c);
            } else throw new IllegalArgumentException("Caracter invalido en " + inicio);
            tokens.add(new Token(tipo, fuente.substring(inicio, i), inicio));
        }
        tokens.add(new Token("EOF", "", i));
        return tokens;
    }
}

// Salida esperada:
// (+ 2 (* 3 4))
// (* (+ 2 3) 4)
// (- (- 9 3) 2)
// Sintaxis invalida
