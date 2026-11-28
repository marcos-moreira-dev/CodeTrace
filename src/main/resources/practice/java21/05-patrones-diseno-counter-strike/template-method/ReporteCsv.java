// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainTemplateCS.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
// Ejecutar: java codetrace.practica.counterstrike.template.MainTemplateCS
// Dominio: Counter-Strike — un resumen de resultados de Counter-Strike se imprime en texto o CSV.
// Patrón: Template Method; ejemplo didáctico independiente.
// Glosario: CT = equipo antiterrorista; CSV = datos separados por coma; mapa = escenario virtual.
package codetrace.practica.counterstrike.template;

/** Variante concreta: mismo flujo, formato de campos separados por coma. */
public final class ReporteCsv extends ProcesoReporte {
    @Override protected String encabezado(String mapa) {
        return "mapa,rondasCt;" + mapa + ",";
    }
    @Override protected String detalle(int rondasCt) {
        return Integer.toString(rondasCt);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTemplateCS.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
 *   java codetrace.practica.counterstrike.template.MainTemplateCS
 * Nota: ReporteCsv.java pertenece al ejercicio; MainTemplateCS.java es su cliente.
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
