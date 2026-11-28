// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainTemplateCS.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
// Ejecutar: java codetrace.practica.counterstrike.template.MainTemplateCS
// Dominio: Counter-Strike — un resumen de resultados de Counter-Strike se imprime en texto o CSV.
// Patrón: Template Method; ejemplo didáctico independiente.
// Glosario: CT = equipo antiterrorista; CSV = datos separados por coma; mapa = escenario virtual.
package codetrace.practica.counterstrike.template;

import java.util.List;

/** Cliente: dos implementaciones, mismo algoritmo generar(). */
public final class MainTemplateCS {
    public static void main(String[] args) {
        // La lista almacena variantes del proceso, no datos del marcador.
        List<ProcesoReporte> formatos = List.of(new ReporteTexto(), new ReporteCsv());
        for (int i = 0; i < formatos.size(); i++) {
            // La plantilla ejecuta encabezado y detalle en un orden fijo.
            String nombre = i == 0 ? "Texto" : "CSV";
            System.out.println(nombre + ": " + formatos.get(i).generar("de_dust2", 7));
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTemplateCS.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
 *   java codetrace.practica.counterstrike.template.MainTemplateCS
 * Nota: MainTemplateCS.java pertenece al ejercicio; MainTemplateCS.java es su cliente.
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
