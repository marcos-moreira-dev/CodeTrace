// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainTemplateMethod.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
// Ejecutar: java codetrace.practica.patrones.templatemethod.MainTemplateMethod
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Informe del torneo que se publica con formatos diferentes.
// Patrón template-method: Fijar el proceso común y personalizar sus pasos concretos.
// Glosario: Reporte: resumen de datos de las rondas, no instrucciones tácticas.

package codetrace.practica.patrones.templatemethod;

/**
 * Template Method: el algoritmo global es final y los pasos varían.
 * Las subclases no pueden alterar el orden de validación y formato.
 */
public abstract class ProcesoReporte {
    public final String ejecutar(String titulo,int total) {
        if(titulo==null||titulo.isBlank()||total<0)
            throw new IllegalArgumentException("Reporte inválido");
        return encabezado(titulo)+detalle(total)+pie();
    }
    protected abstract String encabezado(String titulo);
    protected abstract String detalle(int total);
    protected String pie(){return "\n";} // Hook opcional.
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTemplateMethod.java ProcesoReporte.java ReporteCsv.java ReporteTexto.java
 *   java codetrace.practica.patrones.templatemethod.MainTemplateMethod
 * Nota: ProcesoReporte.java es un componente; la salida corresponde al cliente MainTemplateMethod.java.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
