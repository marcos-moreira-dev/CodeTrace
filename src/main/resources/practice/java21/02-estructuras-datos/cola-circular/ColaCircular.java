// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
// Sin main: es una clase o interfaz para compilar y reutilizar.

public final class ColaCircular {
    private final int[] datos;
    private int inicio;
    private int siguiente;
    private int cantidad;

    public ColaCircular(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("Capacidad inválida");
        datos = new int[capacidad];
    }

    public void encolar(int valor) {
        if (cantidad == datos.length) throw new IllegalStateException("Cola llena");
        datos[siguiente] = valor;
        siguiente = (siguiente + 1) % datos.length;
        cantidad++;
    }

    public int desencolar() {
        if (cantidad == 0) throw new IllegalStateException("Cola vacía");
        int valor = datos[inicio];
        inicio = (inicio + 1) % datos.length;
        cantidad--;
        return valor;
    }

    public int cantidad() {
        return cantidad;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainColaCircular
 * Nota: ColaCircular.java es un componente; la salida corresponde al cliente MainColaCircular.java.
 * Salida esperada:
 *   FIFO y wrap-around verificados
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
