// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CreadorCsv.java CreadorReporte.java CreadorTexto.java ExportadorCsv.java ExportadorResultado.java ExportadorTexto.java MainFactoryMethodCS.java
// Ejecutar: java codetrace.practica.counterstrike.factorymethod.MainFactoryMethodCS
// Dominio: Counter-Strike — publicar resultados de rondas en texto o CSV.
// Patrón: Factory Method; ejercicio educativo no oficial.
// Glosario: CSV = datos separados por comas; reporte = resumen del marcador CT/T del juego.
package codetrace.practica.counterstrike.factorymethod;

/** Producto concreto: campos separados por coma, sin archivos externos. */
public final class ExportadorCsv implements ExportadorResultado {
    @Override public String exportar(int rondasCt) {
        return "equipo,rondas;CT," + rondasCt;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CreadorCsv.java CreadorReporte.java CreadorTexto.java ExportadorCsv.java ExportadorResultado.java ExportadorTexto.java MainFactoryMethodCS.java
 *   java codetrace.practica.counterstrike.factorymethod.MainFactoryMethodCS
 * Nota: ExportadorCsv.java pertenece al ejercicio; el cliente es MainFactoryMethodCS.java.
 * Salida esperada:
 *   Texto: CT=7
 *   CSV: equipo,rondas;CT,7
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
