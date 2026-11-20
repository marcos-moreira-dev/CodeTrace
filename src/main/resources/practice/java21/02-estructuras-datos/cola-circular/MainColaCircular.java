// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" MainColaCircular

/**
 * Cliente: cola FIFO circular implementada sobre un arreglo fijo.
 *
 * <p>Las posiciones avanzan con módulo capacidad para reutilizar
 * celdas liberadas; la implementación no desplaza los elementos.
 * Encolar/desencolar O(1), memoria O(capacidad).
 * Una cola vacía y una llena son estados distintos que el contrato
 * debe distinguir mediante un contador de elementos.
 */
public final class MainColaCircular {
    public static void main(String[] args) {
        ColaCircular cola = new ColaCircular(3);
        cola.encolar(10); cola.encolar(20); cola.encolar(30);
        if (cola.desencolar() != 10) throw new AssertionError();
        cola.encolar(40); // Prueba del índice que vuelve al comienzo.
        if (cola.desencolar() != 20 || cola.desencolar() != 30
                || cola.desencolar() != 40 || cola.cantidad() != 0)
            throw new AssertionError("No se conservó FIFO");
        System.out.println("FIFO y wrap-around verificados");
    }
}

/*
 * Salida esperada de consola:
 *   FIFO y wrap-around verificados
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
