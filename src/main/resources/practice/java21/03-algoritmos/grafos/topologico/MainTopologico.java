// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainTopologico.java OrdenTopologico.java
// Ejecutar: java codetrace.practica.grafostopologico.MainTopologico

package codetrace.practica.grafostopologico;

import java.util.List;

/**
 * Cliente de DAG y planificación de dependencias.
 *
 * <p>Un sistema de construcción de software tiene tareas que deben
 * ejecutarse en cierto orden: compilar antes de probar; probar antes
 * de empaquetar; empaquetar antes de desplegar. No es un árbol,
 * porque varias tareas pueden depender de un mismo componente.
 *
 * <p>Un DAG permite orden topológico, pero un ciclo de dependencias
 * no. El algoritmo de Kahn utiliza grados de entrada y cola FIFO.
 * Complejidad O(V+E); dos órdenes pueden ser distintos y ambos válidos.
 */
public final class MainTopologico {
    public static void main(String[] args) {
        OrdenTopologico pipeline = new OrdenTopologico();
        pipeline.antesQue("Fuentes", "Compilar");
        pipeline.antesQue("Compilar", "Pruebas");
        pipeline.antesQue("Compilar", "Empaquetar");
        pipeline.antesQue("Pruebas", "Desplegar");
        pipeline.antesQue("Empaquetar", "Desplegar");
        List<String> orden = pipeline.ordenar();
        System.out.println("Orden permitido: " + orden);
        if (orden.indexOf("Compilar") >= orden.indexOf("Pruebas"))
            throw new AssertionError("Precondición rota");

        pipeline.antesQue("Desplegar", "Compilar");
        try {
            pipeline.ordenar();
            throw new AssertionError("Se aceptó un ciclo");
        } catch (IllegalStateException esperado) {
            System.out.println("Ciclo detectado correctamente");
        }
    }
}

/*
 * Salida esperada de consola:
 *   Orden permitido: [Fuentes, Compilar, Pruebas, Empaquetar, Desplegar]
 *   Ciclo detectado correctamente
 * Esta implementación de Kahn conserva el orden de inserción ante empates.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTopologico.java OrdenTopologico.java
 *   java codetrace.practica.grafostopologico.MainTopologico
 * Salida esperada:
 *   Orden permitido: [Fuentes, Compilar, Pruebas, Empaquetar, Desplegar]
 *   Ciclo detectado correctamente
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
