// Tema:
// El evaluador recorre un AST de expresiones enteras. Primero evalúa hijos y después aplica el operador. Math.addExact y similares evitan desbordamiento silencioso en suma/resta/producto; división entera y división por cero tienen sus propias reglas.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Expresion.java Evaluador.java MainEvaluador.java
// Ejecutar: java MainEvaluador

public record Expresion(String operador, int valor, Expresion a, Expresion b) {
    public static Expresion numero(int n) { return new Expresion("NUM", n, null, null); }
    public static Expresion binaria(String op, Expresion a, Expresion b) {
        return new Expresion(op, 0, a, b);
    }
}

// Salida esperada:
// 14
// Division invalida
