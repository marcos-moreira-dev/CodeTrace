// Tema:
// Una prueba unitaria prepara entradas, ejecuta una unidad y compara con el resultado esperado. AssertionError explícito produce salida no cero ante fallo, incluso sin habilitar assert. No requiere JUnit.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 Calculadora.java PruebaCalculadora.java
// Ejecutar: java PruebaCalculadora

public class Calculadora {
    public static int sumar(int a, int b) { return Math.addExact(a, b); }
    public static int dividir(int a, int b) { return Math.divideExact(a, b); }
}

// Salida esperada:
// Division por cero comprobada
// Pruebas: 3 OK
