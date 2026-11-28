// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ColaEnlazada.java MainColaEnlazada.java
// Ejecutar: java codetrace.practica.estructuras.colaenlazada.MainColaEnlazada

package codetrace.practica.estructuras.colaenlazada;

/**
 * Cliente cola enlazada: FIFO con crecimiento por nodos.
 *
 * <p>La cola circular existente tiene capacidad fija y almacenamiento
 * continuo. Esta cola crea un nodo por inserción, no requiere mover
 * elementos y no está limitada por una capacidad predeterminada.
 * Encolar y desencolar O(1). Se comprueba el caso sutil de vaciar
 * por completo y volver a insertar, donde tail debe reiniciarse.
 */
public final class MainColaEnlazada {
    public static void main(String[] args) {
        var q = new ColaEnlazada<String>();
        q.encolar("A"); q.encolar("B");
        if (!"A".equals(q.desencolar()) || !"B".equals(q.desencolar()))
            throw new AssertionError("FIFO");
        q.encolar("C");
        if (!"C".equals(q.desencolar()) || q.cantidad()!=0)
            throw new AssertionError("Cola reiniciada");
        System.out.println("FIFO enlazada OK");
    }
}

/*
 * Salida esperada de consola:
 *   FIFO enlazada OK
 * La cola devuelve A, B y luego C, incluso después de vaciarse.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ColaEnlazada.java MainColaEnlazada.java
 *   java codetrace.practica.estructuras.colaenlazada.MainColaEnlazada
 * Salida esperada:
 *   FIFO enlazada OK
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
