// Tema:
// El análisis léxico agrupa caracteres en tokens y registra su posición. Los espacios no son tokens, EOF marca fin de entrada y los caracteres ajenos a la gramática se rechazan.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Token.java Lexer.java MainLexer.java
// Ejecutar: java MainLexer

public class MainLexer {
    public static void main(String[] args) throws Exception {
        for (Token t : Lexer.analizar("pan + 12"))
            System.out.println(t.tipo() + ":" + t.texto() + "@" + t.posicion());
        try { Lexer.analizar("2 @ 3"); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}

// Salida esperada:
// ID:pan@0
// +:+@4
// NUM:12@6
// EOF:@8
// Caracter invalido en 2
