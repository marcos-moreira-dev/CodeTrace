// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

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
final class ReporteTexto extends ProcesoReporte {
    protected String encabezado(String titulo){return "== "+titulo+" ==\n";}
    protected String detalle(int total){return "Total: "+total;}
}
final class ReporteCsv extends ProcesoReporte {
    protected String encabezado(String titulo){return "titulo,total\n";}
    protected String detalle(int total){return "ventas,"+total;}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.templatemethod.MainTemplateMethod
 * Nota: ProcesoReporte.java es un componente; la salida corresponde al cliente MainTemplateMethod.java.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
