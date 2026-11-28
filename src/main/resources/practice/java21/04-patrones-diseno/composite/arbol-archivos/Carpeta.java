// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . Archivo.java Carpeta.java Elemento.java MainComposite.java
// Ejecutar: java codetrace.practica.patrones.composite.MainComposite
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Árbol de archivos de mapas y recursos del videojuego.
// Patrón composite: Tratar hojas y contenedores con una misma interfaz.
// Glosario: Mapa: escenario virtual; recurso: archivo usado por el juego.
// Este archivo define exactamente un tipo reutilizable, sin main.

package codetrace.practica.patrones.composite;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.composite.MainComposite; salida esperada: Bytes: 220. */

import java.util.ArrayList;
import java.util.List;

/** Componente compuesto que suma recursivamente el tamaño de sus hijos. */
public final class Carpeta implements Elemento {
    private final String nombre;
    private final List<Elemento> hijos = new ArrayList<>();

    public Carpeta(String nombre) { this.nombre = nombre; }

    public void agregar(Elemento elemento) {
        if (elemento == this) throw new IllegalArgumentException("Ciclo directo");
        hijos.add(elemento);
    }

    @Override public int bytes() {
        return hijos.stream().mapToInt(Elemento::bytes).sum();
    }

    public String nombre() { return nombre; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . Archivo.java Carpeta.java Elemento.java MainComposite.java
 *   java codetrace.practica.patrones.composite.MainComposite
 * Nota: Carpeta.java es un componente; la salida corresponde al cliente MainComposite.java.
 * Salida esperada:
 *   Bytes: 220
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
