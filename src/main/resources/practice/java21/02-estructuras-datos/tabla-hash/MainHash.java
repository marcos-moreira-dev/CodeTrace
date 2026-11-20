// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.hash.MainHash

package codetrace.practica.hash;

/**
 * Cliente de una tabla hash de direccionamiento abierto.
 *
 * <p>Las claves "FB" y "Ea" comparten hashCode en Java. La prueba
 * demuestra que una colisión no implica reemplazar una clave por otra.
 * Se distinguen tamaño, capacidad y factor de carga. El contrato
 * retorna null cuando no encuentra una clave, sin crearla.
 *
 * <p>La tabla es didáctica y utiliza sondeo lineal; no pretende
 * reproducir internamente la implementación de HashMap.
 */
public final class MainHash {
    public static void main(String[] args) {
        TablaHash tabla = new TablaHash(8);
        tabla.poner("FB", 10);
        tabla.poner("Ea", 20);
        tabla.poner("FB", 15);
        System.out.println("FB=" + tabla.obtener("FB"));
        System.out.println("Ea=" + tabla.obtener("Ea"));
        System.out.println("Ausente=" + tabla.obtener("otro"));
        System.out.println("Claves distintas=" + tabla.tamaño());
    }
}

/*
 * Salida esperada de consola:
 *   FB=15
 *   Ea=20
 *   Ausente=null
 *   Claves distintas=2
 * Las claves FB y Ea colisionan, pero conservan valores independientes.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.hash.MainHash
 * Salida esperada:
 *   FB=15
 *   Ea=20
 *   Ausente=null
 *   Claves distintas=2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
