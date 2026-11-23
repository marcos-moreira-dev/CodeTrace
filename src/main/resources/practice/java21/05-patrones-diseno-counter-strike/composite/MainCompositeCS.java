// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.composite.MainCompositeCS
// Dominio: Counter-Strike — árbol de mapas y archivos del juego, sin cargar contenidos protegidos.
// Patrón: Composite; ejercicio educativo no oficial.
// Glosario: Mapa = escenario virtual; recurso = archivo del videojuego; carpeta = contenedor de recursos.
package codetrace.practica.counterstrike.composite;

/** Cliente: no necesita preguntar si cada recurso es archivo o carpeta. */
public final class MainCompositeCS {
    public static void main(String[] args) {
        CarpetaMapa mapas = new CarpetaMapa("maps");
        CarpetaMapa oficiales = new CarpetaMapa("oficiales");

        // Estos nombres son ejemplos, no se copian archivos reales del juego.
        oficiales.agregar(new ArchivoMapa("de_dust2"));
        oficiales.agregar(new ArchivoMapa("de_inferno"));
        mapas.agregar(oficiales);
        mapas.agregar(new ArchivoMapa("cs_office"));

        // El contador recorre recursivamente los hijos del árbol.
        System.out.println("Mapas registrados: " + mapas.contar());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.composite.MainCompositeCS
 * Nota: MainCompositeCS.java pertenece al ejercicio; el cliente es MainCompositeCS.java.
 * Salida esperada:
 *   Mapas registrados: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
