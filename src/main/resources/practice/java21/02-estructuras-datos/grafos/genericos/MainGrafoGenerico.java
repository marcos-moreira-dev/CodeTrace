// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GrafoGenerico.java MainGrafoGenerico.java Vertice.java
// Ejecutar: java codetrace.practica.grafosgenericos.MainGrafoGenerico

package codetrace.practica.grafosgenericos;

/**
 * Cliente: una CDN como grafo genérico con identificadores estables.
 *
 * <p>Un servidor tiene una región y una dirección IP; un vértice
 * dispone de un id técnico duradero independiente de esos atributos.
 * La latencia entre dos nodos es el peso de una arista dirigida.
 *
 * <p>El modelo se aproxima al de tu repositorio de estructuras de datos,
 * pero evita copiarlo sin revisión. La clase Vertice define la identidad;
 * GrafoGenerico administra adyacencias; este cliente prepara la red.
 * Conexión de servidores no significa árbol, pues puede haber ciclos
 * y múltiples rutas alternativas.
 *
 * <p>Al comparar un segundo objeto con el mismo id, se ve por qué
 * las estructuras hash necesitan equals/hashCode coherentes.
 */
public final class MainGrafoGenerico {
    record Servidor(String region, String ip) { }

    public static void main(String[] args) {
        var gye = new Vertice<>("GYE", new Servidor("Guayaquil", "10.0.0.1"));
        var uio = new Vertice<>("UIO", new Servidor("Quito", "10.0.0.2"));
        var cue = new Vertice<>("CUE", new Servidor("Cuenca", "10.0.0.3"));

        GrafoGenerico<Servidor> red = new GrafoGenerico<>();
        red.conectar(gye, uio, 12);
        red.conectar(gye, cue, 18);
        red.conectar(uio, cue, 8);
        System.out.println("Vértices: " + red.vertices().size());
        System.out.println("Latencias desde GYE: " + red.vecinos(gye));

        var mismoId = new Vertice<>("GYE",
                new Servidor("Guayaquil", "10.9.9.9"));
        if (!red.vertices().contains(mismoId) || red.aristas() != 3)
            throw new AssertionError("Identidad de vértice incorrecta");
    }
}

/*
 * Salida esperada de consola:
 *   Vértices: 3
 *   Latencias desde GYE: {UIO [Servidor[region=Quito, ip=10.0.0.2]]=12.0, CUE [Servidor[region=Cuenca, ip=10.0.0.3]]=18.0}
 * El mapa mantiene orden de inserción y los records muestran sus componentes.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GrafoGenerico.java MainGrafoGenerico.java Vertice.java
 *   java codetrace.practica.grafosgenericos.MainGrafoGenerico
 * Salida esperada:
 *   Vértices: 3
 *   Latencias desde GYE: {UIO [Servidor[region=Quito, ip=10.0.0.2]]=12.0, CUE [Servidor[region=Cuenca, ip=10.0.0.3]]=18.0}
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
