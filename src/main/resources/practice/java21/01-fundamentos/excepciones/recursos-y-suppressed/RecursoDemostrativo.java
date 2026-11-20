// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: usar desde la clase cliente Main... del mismo ejemplo

/**
 * Recurso simulado que implementa AutoCloseable.
 *
 * Permite estudiar que close() se ejecuta incluso si el cuerpo del
 * bloque falla. La excepción de cierre se almacena como suprimida
 * cuando ya existía un fallo principal. No abre archivos reales.
 */
public final class RecursoDemostrativo implements AutoCloseable {
    private final String nombre;
    public RecursoDemostrativo(String nombre) {
        this.nombre = nombre;
        System.out.println("Abrir: " + nombre);
    }
    public void procesar() {
        throw new IllegalStateException("Error del proceso " + nombre);
    }
    @Override public void close() {
        System.out.println("Cerrar: " + nombre);
        throw new IllegalStateException("Error al cerrar " + nombre);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainRecursosYSuppressed
 * Nota: RecursoDemostrativo.java es un componente; la salida corresponde al cliente MainRecursosYSuppressed.java.
 * Salida esperada:
 *   Abrir: demostración
 *   Cerrar: demostración
 *   Principal: Error del proceso demostración
 *   Suprimidas: 1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
