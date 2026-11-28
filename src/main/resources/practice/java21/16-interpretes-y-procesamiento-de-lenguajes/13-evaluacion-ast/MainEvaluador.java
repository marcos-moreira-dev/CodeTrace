// Tema:
// El evaluador recorre un AST de expresiones enteras. Primero evalúa hijos y después aplica el operador. Math.addExact y similares evitan desbordamiento silencioso en suma/resta/producto; división entera y división por cero tienen sus propias reglas.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 Expresion.java Evaluador.java MainEvaluador.java
// Ejecutar: java MainEvaluador

public class MainEvaluador {
    public static void main(String[] args) throws Exception {
        Expresion arbol = Expresion.binaria("+", Expresion.numero(2),
                Expresion.binaria("*", Expresion.numero(3), Expresion.numero(4)));
        System.out.println(Evaluador.evaluar(arbol));
        try { Evaluador.evaluar(Expresion.binaria("/", Expresion.numero(4), Expresion.numero(0))); }
        catch (ArithmeticException e) { System.out.println("Division invalida"); }
    }
}

// Salida esperada:
// 14
// Division invalida
