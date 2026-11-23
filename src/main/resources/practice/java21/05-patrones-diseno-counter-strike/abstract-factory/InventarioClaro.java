// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
// Dominio: Counter-Strike — un HUD permite presentar el marcador y el inventario con temas claro u oscuro.
// Patrón: Abstract Factory; ejercicio independiente, no oficial.
// Glosario: HUD = indicadores superpuestos a la pantalla; inventario = lista de objetos virtuales; MP9 = subfusil representado como elemento del juego.
package codetrace.practica.counterstrike.abstractfactory;

/** Producto claro del inventario del videojuego. */
public final class InventarioClaro implements EtiquetaInventario {
    @Override public String dibujar(String item) { return "[CLARO] equipo=" + item; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
 * Nota: InventarioClaro.java se estudia junto al cliente MainAbstractFactoryCS.java.
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
