// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.listacircular.MainListaCircular

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
 *   Turnos: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
