// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CreadorExportadores.java CreadorTexto.java Exportador.java ExportadorTexto.java MainFactoryMethod.java
// Ejecutar: java codetrace.factorymethod.MainFactoryMethod
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reportes de un torneo competitivo ficticio.
// Patrón factory-method: Delegar la creación del tipo de exportador a un subtipo.
// Glosario: Reporte: resumen de resultados de rondas.
package codetrace.factorymethod;
public final class ExportadorTexto implements Exportador {
    @Override public String exportar(String contenido) {
        return "TEXTO: " + contenido;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CreadorExportadores.java CreadorTexto.java Exportador.java ExportadorTexto.java MainFactoryMethod.java
 *   java codetrace.factorymethod.MainFactoryMethod
 * Nota: ExportadorTexto.java es un componente; la salida corresponde al cliente MainFactoryMethod.java.
 * Salida esperada:
 *   TEXTO: Factura 001
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
