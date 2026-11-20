// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
// Sin main: es una clase o interfaz para compilar y reutilizar.

import java.util.NoSuchElementException;

public final class Pila<T> {
    private final Object[] datos = new Object[16];
    private int cantidad;

    public void apilar(T valor) {
        if (cantidad == datos.length) throw new IllegalStateException("Llena");
        datos[cantidad++] = valor;
    }

    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (cantidad == 0) throw new NoSuchElementException();
        T valor = (T) datos[--cantidad];
        datos[cantidad] = null;
        return valor;
    }

    public int size() {
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
 *   java -cp $out MainPila
 * Nota: Pila.java es un componente; la salida corresponde al cliente MainPila.java.
 * Salida esperada:
 *   LIFO comprobado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
