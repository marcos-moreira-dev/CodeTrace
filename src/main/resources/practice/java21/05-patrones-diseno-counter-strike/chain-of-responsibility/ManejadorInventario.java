// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.cadena.MainCadenaCS
// Dominio: Counter-Strike — avisos de una ronda enviados a responsables.
// Patrón: Chain of Responsibility. Cada componente tiene su propia responsabilidad.
// Glosario: Rehén = personaje virtual que se rescata; MP9 = nombre de subfusil en el inventario del videojuego; minimapa = vista pequeña del escenario.
package codetrace.practica.counterstrike.cadena;

/** Atiende cambios del inventario virtual, no de armas reales. */
public final class ManejadorInventario extends ManejadorAviso {
    @Override public String atender(AvisoRonda aviso) {
        if (aviso.tipo() == TipoAviso.EQUIPAMIENTO)
            return "Inventario: " + aviso.detalle();
        return delegar(aviso);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.cadena.MainCadenaCS
 * Nota: ManejadorInventario.java pertenece al ejercicio; el punto de entrada es MainCadenaCS.java.
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
