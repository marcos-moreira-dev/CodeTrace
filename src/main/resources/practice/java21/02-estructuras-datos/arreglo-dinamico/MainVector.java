// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.vector.MainVector

package codetrace.practica.vector;

/**
 * Cliente para distinguir longitud lógica y capacidad de un vector.
 *
 * <p>Se agregan seis valores a un almacenamiento que inicia con
 * capacidad dos. La estrategia geométrica amortiza las copias. El
 * cliente demuestra la API pública sin tocar el arreglo interno.
 *
 * <p>Roles: VectorEnteros administra memoria y valida índices;
 * MainVector prepara un caso, ejecuta operaciones y presenta resultados.
 */
public final class MainVector {
    public static void main(String[] args) {
        VectorEnteros vector = new VectorEnteros();
        for (int i = 1; i <= 6; i++) vector.agregar(i * 10);
        System.out.println("Elementos: " + vector.tamaño());
        System.out.println("Capacidad: " + vector.capacidad());
        System.out.println("Tercero: " + vector.obtener(2));
    }
}

/*
 * Salida esperada de consola:
 *   Elementos: 6
 *   Capacidad: 8
 *   Tercero: 30
 * La capacidad crece geométricamente 2 -> 4 -> 8, sin cambiar el tamaño lógico.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.vector.MainVector
 * Salida esperada:
 *   Elementos: 6
 *   Capacidad: 8
 *   Tercero: 30
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
