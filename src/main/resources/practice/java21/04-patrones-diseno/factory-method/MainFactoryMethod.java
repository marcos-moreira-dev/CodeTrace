// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . CreadorExportadores.java CreadorTexto.java Exportador.java ExportadorTexto.java MainFactoryMethod.java
// Ejecutar: java codetrace.factorymethod.MainFactoryMethod
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reportes de un torneo competitivo ficticio.
// Patrón factory-method: Delegar la creación del tipo de exportador a un subtipo.
// Glosario: Reporte: resumen de resultados de rondas.
package codetrace.factorymethod;
/**
 * Cliente Factory Method: el creador abstracto define el flujo de
 * preparación y la subclase concreta decide el Exportador. No es
 * una función fábrica: el polimorfismo se aplica al creador.
 *
 * Archivos: CreadorExportadores, CreadorTexto, Exportador y
 * ExportadorTexto. El cliente depende de la abstracción creadora.
 */
public final class MainFactoryMethod {
    public static void main(String[] args) {
        CreadorExportadores creador = new CreadorTexto();
        System.out.println(creador.preparar("Factura 001"));
    }
}

/*
 * Salida esperada de consola:
 *   TEXTO: Factura 001
 * La subclase creadora escoge ExportadorTexto por Factory Method.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CreadorExportadores.java CreadorTexto.java Exportador.java ExportadorTexto.java MainFactoryMethod.java
 *   java codetrace.factorymethod.MainFactoryMethod
 * Salida esperada:
 *   TEXTO: Factura 001
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
