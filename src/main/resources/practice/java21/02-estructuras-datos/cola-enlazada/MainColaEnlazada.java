// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.colaenlazada.MainColaEnlazada

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
