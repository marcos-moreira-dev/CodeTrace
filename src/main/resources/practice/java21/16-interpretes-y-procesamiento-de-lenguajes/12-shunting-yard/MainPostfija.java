// Tema:
// Shunting Yard convierte notación infija en postfija mediante una pila de operadores. Se trabaja con tokens separados por espacios, operadores binarios asociativos a izquierda y paréntesis; se valida la alternancia de operandos y operadores.
// Modalidad: modelo de procesamiento de lenguajes.
// Compilar: javac --release 21 -encoding UTF-8 ShuntingYard.java MainPostfija.java
// Ejecutar: java MainPostfija

public class MainPostfija {
    public static void main(String[] args) throws Exception {
        System.out.println(ShuntingYard.convertir("2 + 3 * 4"));
        System.out.println(ShuntingYard.convertir("( 2 + 3 ) * 4"));
    }
}

// Salida esperada:
// 2 3 4 * +
// 2 3 + 4 *
