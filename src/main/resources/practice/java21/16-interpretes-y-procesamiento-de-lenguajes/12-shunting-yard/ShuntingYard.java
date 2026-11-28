// Tema:
// Shunting Yard convierte notación infija en postfija mediante una pila de operadores. Se trabaja con tokens separados por espacios, operadores binarios asociativos a izquierda y paréntesis; se valida la alternancia de operandos y operadores.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 ShuntingYard.java MainPostfija.java
// Ejecutar: java MainPostfija

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ShuntingYard {
    private static int prioridad(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }
    public static String convertir(String expresion) {
        if (expresion.isBlank() || expresion.length() > 256) throw new IllegalArgumentException("Entrada");
        List<String> salida = new ArrayList<>();
        ArrayDeque<String> pila = new ArrayDeque<>();
        boolean esperaOperando = true;
        for (String t : expresion.strip().split("\\s+")) {
            if (t.matches("[0-9]+") && esperaOperando) {
                salida.add(t);
                esperaOperando = false;
            } else if (t.equals("(") && esperaOperando) {
                pila.push(t);
            } else if (t.equals(")") && !esperaOperando) {
                while (!pila.isEmpty() && !pila.peek().equals("(")) salida.add(pila.pop());
                if (pila.isEmpty()) throw new IllegalArgumentException("Parentesis");
                pila.pop();
            } else if (prioridad(t) > 0 && !esperaOperando) {
                while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(t)) salida.add(pila.pop());
                pila.push(t);
                esperaOperando = true;
            } else throw new IllegalArgumentException("Token inesperado: " + t);
        }
        if (esperaOperando) throw new IllegalArgumentException("Operando esperado");
        while (!pila.isEmpty()) {
            String op = pila.pop();
            if (op.equals("(")) throw new IllegalArgumentException("Parentesis");
            salida.add(op);
        }
        return String.join(" ", salida);
    }
}

// Salida esperada:
// 2 3 4 * +
// 2 3 + 4 *
