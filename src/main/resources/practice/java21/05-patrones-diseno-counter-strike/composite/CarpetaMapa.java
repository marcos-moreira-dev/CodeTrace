// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ArchivoMapa.java CarpetaMapa.java MainCompositeCS.java RecursoMapa.java
// Ejecutar: java codetrace.practica.counterstrike.composite.MainCompositeCS
// Dominio: Counter-Strike — árbol de mapas y archivos del juego, sin cargar contenidos protegidos.
// Patrón: Composite; ejercicio educativo no oficial.
// Glosario: Mapa = escenario virtual; recurso = archivo del videojuego; carpeta = contenedor de recursos.
package codetrace.practica.counterstrike.composite;

import java.util.ArrayList;
import java.util.List;

/** Compuesto: contiene recursos que pueden ser hojas u otras carpetas. */
public final class CarpetaMapa implements RecursoMapa {
    private final String nombre;
    private final List<RecursoMapa> hijos = new ArrayList<>();
    public CarpetaMapa(String nombre) { this.nombre = nombre; }
    public void agregar(RecursoMapa recurso) {
        if (recurso == this) throw new IllegalArgumentException("Ciclo directo");
        hijos.add(recurso);
    }
    @Override public int contar() {
        int cantidad = 0;
        // El mismo método contar() funciona para un archivo y para una carpeta.
        for (RecursoMapa hijo : hijos) cantidad += hijo.contar();
        return cantidad;
    }
    public String nombre() { return nombre; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ArchivoMapa.java CarpetaMapa.java MainCompositeCS.java RecursoMapa.java
 *   java codetrace.practica.counterstrike.composite.MainCompositeCS
 * Nota: CarpetaMapa.java pertenece al ejercicio; el cliente es MainCompositeCS.java.
 * Salida esperada:
 *   Mapas registrados: 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
