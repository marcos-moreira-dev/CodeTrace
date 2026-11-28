// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CreadorCsv.java CreadorReporte.java CreadorTexto.java ExportadorCsv.java ExportadorResultado.java ExportadorTexto.java MainFactoryMethodCS.java
// Ejecutar: java codetrace.practica.counterstrike.factorymethod.MainFactoryMethodCS
// Dominio: Counter-Strike — publicar resultados de rondas en texto o CSV.
// Patrón: Factory Method; ejercicio educativo no oficial.
// Glosario: CSV = datos separados por comas; reporte = resumen del marcador CT/T del juego.
package codetrace.practica.counterstrike.factorymethod;

import java.util.List;

/** Cliente: prueba dos creadores con el mismo procedimiento publicar(). */
public final class MainFactoryMethodCS {
    public static void main(String[] args) {
        // Estos son CREADORES, no los exportadores concretos.
        List<CreadorReporte> creadores = List.of(new CreadorTexto(), new CreadorCsv());
        // La misma solicitud da dos formatos según el creador de cada vuelta.
        for (int i = 0; i < creadores.size(); i++) {
            String formato = i == 0 ? "Texto" : "CSV";
            System.out.println(formato + ": " + creadores.get(i).publicar(7));
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CreadorCsv.java CreadorReporte.java CreadorTexto.java ExportadorCsv.java ExportadorResultado.java ExportadorTexto.java MainFactoryMethodCS.java
 *   java codetrace.practica.counterstrike.factorymethod.MainFactoryMethodCS
 * Nota: MainFactoryMethodCS.java pertenece al ejercicio; el cliente es MainFactoryMethodCS.java.
 * Salida esperada:
 *   Texto: CT=7
 *   CSV: equipo,rondas;CT,7
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
