// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainTemplateMethod.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
// Ejecutar: java codetrace.practica.patrones.templatemethod.MainTemplateMethod
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Informe del torneo que se publica con formatos diferentes.
// Patrón template-method: Fijar el proceso común y personalizar sus pasos concretos.
// Glosario: Reporte: resumen de datos de las rondas, no instrucciones tácticas.

package codetrace.practica.patrones.templatemethod;

/**
 * Cliente Template Method: secuencia fija, pasos personalizables.
 *
 * <p>Una clase abstracta implementa ejecutar() como método FINAL:
 * valida entrada y fija el orden de los pasos. Subclases sustituyen
 * el formato. Es diferente de Strategy, donde la política se pasa
 * como una dependencia intercambiable; aquí se personaliza mediante
 * herencia. En lenguajes funcionales suele preferirse composición.
 */
public final class MainTemplateMethod {
    public static void main(String[] args) {
        ProcesoReporte texto=new ReporteTexto();
        ProcesoReporte csv=new ReporteCsv();
        String a=texto.ejecutar("ventas",15);
        String b=csv.ejecutar("ventas",15);
        if(!a.contains("Total: 15")||!b.contains("ventas,15"))
            throw new AssertionError("Template Method");
        System.out.println(a+b);
    }
}

/*
 * Salida esperada de consola:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 *   (y una línea en blanco: println agrega un salto al texto que ya termina en \n)
 * Se aplican los mismos pasos de validación, encabezado, detalle y pie.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTemplateMethod.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
 *   java codetrace.practica.patrones.templatemethod.MainTemplateMethod
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
