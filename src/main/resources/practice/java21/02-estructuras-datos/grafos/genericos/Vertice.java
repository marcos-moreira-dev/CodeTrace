// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GrafoGenerico.java MainGrafoGenerico.java Vertice.java
// Ejecutar: java codetrace.practica.grafosgenericos.MainGrafoGenerico

package codetrace.practica.grafosgenericos;

import java.util.Objects;

/**
 * Vértice con identificador estable y dato genérico T.
 *
 * <p>La igualdad depende del id, no de la información transportada.
 * Dos objetos con el mismo id representan el mismo nodo lógico
 * aunque el payload difiera. Eso permite a la red conservar identidad
 * cuando varían las propiedades de los servidores.
 *
 * <p>No es un record porque deliberadamente sobrescribimos la igualdad
 * semántica por id; un record normalmente incluye todos sus componentes
 * en equals y hashCode. El id es inmutable para no romper mapas hash.
 */
public final class Vertice<T> {
    private final String id;
    private final T dato;

    public Vertice(String id, T dato) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID");
        this.id = id;
        this.dato = Objects.requireNonNull(dato);
    }

    public String id() { return id; }
    public T dato() { return dato; }

    @Override public boolean equals(Object otro) {
        return otro instanceof Vertice<?> vertice && id.equals(vertice.id);
    }

    @Override public int hashCode() { return id.hashCode(); }
    @Override public String toString() { return id + " [" + dato + "]"; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GrafoGenerico.java MainGrafoGenerico.java Vertice.java
 *   java codetrace.practica.grafosgenericos.MainGrafoGenerico
 * Nota: Vertice.java es un componente; la salida corresponde al cliente MainGrafoGenerico.java.
 * Salida esperada:
 *   Vértices: 3
 *   Latencias desde GYE: {UIO [Servidor[region=Quito, ip=10.0.0.2]]=12.0, CUE [Servidor[region=Cuenca, ip=10.0.0.3]]=18.0}
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
