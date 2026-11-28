// Tema:
// El evaluador recorre un AST de expresiones enteras. Primero evalúa hijos y después aplica el operador. Math.addExact y similares evitan desbordamiento silencioso en suma/resta/producto; división entera y división por cero tienen sus propias reglas.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Expresion.java Evaluador.java MainEvaluador.java
// Ejecutar: java MainEvaluador

public class Evaluador {
    public static int evaluar(Expresion e) {
        if (e.operador().equals("NUM")) return e.valor();
        int a = evaluar(e.a()), b = evaluar(e.b());
        return switch (e.operador()) {
            case "+" -> Math.addExact(a, b);
            case "-" -> Math.subtractExact(a, b);
            case "*" -> Math.multiplyExact(a, b);
            case "/" -> Math.divideExact(a, b);
            default -> throw new IllegalArgumentException("Operador");
        };
    }
}

// Salida esperada:
// 14
// Division invalida
