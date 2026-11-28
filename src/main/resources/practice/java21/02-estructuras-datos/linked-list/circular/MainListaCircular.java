// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ListaCircular.java MainListaCircular.java
// Ejecutar: java codetrace.practica.estructuras.listacircular.MainListaCircular

package codetrace.practica.estructuras.listacircular;

import java.util.List;

/**
 * Cliente Linked List circular: turno rotatorio sin fin de cadena.
 *
 * <p>Una lista lineal termina en null; aquí el último nodo vuelve al
 * primero. El peligro didáctico es recorrer con while(actual!=null),
 * que jamás terminaría. Se usa el contador para limitar el recorrido.
 * Rotar la referencia del último cuesta O(1). Ejemplo de asignación
 * de turnos tipo round-robin; no es una cola concurrente.
 */
public final class MainListaCircular {
    public static void main(String[] args) {
        var turnos = new ListaCircular<String>();
        if (!turnos.valores().isEmpty()) throw new AssertionError("Vacía");
        turnos.agregar("Ana"); turnos.agregar("Luis"); turnos.agregar("Mia");
        turnos.rotar();
        if (!turnos.valores().equals(List.of("Luis", "Mia", "Ana")))
            throw new AssertionError("Enlace circular");
        System.out.println("Turnos: " + turnos.valores());
    }
}

/*
 * Salida esperada de consola:
 *   Turnos: [Luis, Mia, Ana]
 * La rotación hace que el último turno pase al final del recorrido.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ListaCircular.java MainListaCircular.java
 *   java codetrace.practica.estructuras.listacircular.MainListaCircular
 * Salida esperada:
 *   Turnos: [Luis, Mia, Ana]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
