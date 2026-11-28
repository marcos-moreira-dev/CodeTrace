// Tema:
// Una prueba unitaria prepara entradas, ejecuta una unidad y compara con el resultado esperado. AssertionError explícito produce salida no cero ante fallo, incluso sin habilitar assert. No requiere JUnit.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 Calculadora.java PruebaCalculadora.java
// Ejecutar: java PruebaCalculadora

public class PruebaCalculadora {
    static void igual(int esperado, int real) {
        if (esperado != real) throw new AssertionError(esperado + " != " + real);
    }

    public static void main(String[] args) throws Exception {
        igual(5, Calculadora.sumar(2, 3));
        igual(-2, Calculadora.dividir(-6, 3));
        try {
            Calculadora.dividir(3, 0);
            throw new AssertionError("Se esperaba error");
        } catch (ArithmeticException esperado) {
            System.out.println("Division por cero comprobada");
        }
        System.out.println("Pruebas: 3 OK");
    }
}

// Salida esperada:
// Division por cero comprobada
// Pruebas: 3 OK
