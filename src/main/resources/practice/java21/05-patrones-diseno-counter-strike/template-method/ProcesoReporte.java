// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainTemplateCS.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
// Ejecutar: java codetrace.practica.counterstrike.template.MainTemplateCS
// Dominio: Counter-Strike — un resumen de resultados de Counter-Strike se imprime en texto o CSV.
// Patrón: Template Method; ejemplo didáctico independiente.
// Glosario: CT = equipo antiterrorista; CSV = datos separados por coma; mapa = escenario virtual.
package codetrace.practica.counterstrike.template;

/** Template Method: define la SECUENCIA invariable del reporte. */
public abstract class ProcesoReporte {
    public final String generar(String mapa, int rondasCt) {
        // Método final: todas las variantes siguen estas dos etapas.
        String inicio = encabezado(mapa);
        String datos = detalle(rondasCt);
        return inicio + datos;
    }

    protected abstract String encabezado(String mapa);
    protected abstract String detalle(int rondasCt);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTemplateCS.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
 *   java codetrace.practica.counterstrike.template.MainTemplateCS
 * Nota: ProcesoReporte.java pertenece al ejercicio; MainTemplateCS.java es su cliente.
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
