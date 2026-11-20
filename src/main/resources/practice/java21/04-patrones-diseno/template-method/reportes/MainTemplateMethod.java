// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.patrones.templatemethod.MainTemplateMethod

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
