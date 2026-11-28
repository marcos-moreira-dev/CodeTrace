// Tema:
// El análisis léxico agrupa caracteres en tokens y registra su posición. Los espacios no son tokens, EOF marca fin de entrada y los caracteres ajenos a la gramática se rechazan.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Token.java Lexer.java MainLexer.java
// Ejecutar: java MainLexer

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
// ID:pan@0
// +:+@4
// NUM:12@6
// EOF:@8
// Caracter invalido en 2
