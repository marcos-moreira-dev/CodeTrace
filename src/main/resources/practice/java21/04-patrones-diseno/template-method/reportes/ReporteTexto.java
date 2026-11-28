// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainTemplateMethod.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
// Ejecutar: java codetrace.practica.patrones.templatemethod.MainTemplateMethod
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Informe del torneo que se publica con formatos diferentes.
// Patrón template-method: Fijar el proceso común y personalizar sus pasos concretos.
// Glosario: Reporte: resumen de datos de las rondas, no instrucciones tácticas.
// Salida esperada: == ventas == / Total: 15 / titulo,total / ventas,15
package codetrace.practica.patrones.templatemethod;

/** Variante concreta: personaliza las fases del reporte de texto. */
public final class ReporteTexto extends ProcesoReporte {
    @Override protected String encabezado(String titulo) {
        return "== " + titulo + " ==\n";
    }
    @Override protected String detalle(int total) {
        return "Total: " + total;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTemplateMethod.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
 *   java codetrace.practica.patrones.templatemethod.MainTemplateMethod
 * Nota: ReporteTexto.java es un componente; la salida corresponde al cliente MainTemplateMethod.java.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
