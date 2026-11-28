// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
// Ejecutar: java codetrace.practica.patrones.command.MainCommand
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Editor de las notas de una ronda en un juego ficticio.
// Patrón command: Deshacer o rehacer acciones encapsuladas.
// Glosario: Ronda: segmento breve de una partida; deshacer: restaurar la nota anterior.

package codetrace.practica.patrones.command;

import java.util.ArrayDeque;

/**
 * Cliente Command con ejecución y deshacer (Undo).
 *
 * <p>Un Comando encapsula una petición; Editor es el receptor que
 * realmente cambia el texto. El historial almacena comandos ya
 * ejecutados. Esta separación permite registrar, deshacer o enviar
 * peticiones sin acoplar controles de interfaz al dominio.
 *
 * <p>El ejemplo asume deshacer en orden LIFO; ejecutar el mismo
 * comando simultáneamente no está permitido. Sin hilos ni archivos.
 */
public final class MainCommand {
    public static void main(String[] args) {
        var editor=new Editor();
        var historial=new ArrayDeque<Comando>();
        for(String texto:new String[]{"Hola"," mundo"}) {
            Comando c=new AgregarTexto(editor,texto);
            c.ejecutar();historial.push(c);
        }
        if(!editor.texto().equals("Hola mundo"))throw new AssertionError();
        historial.pop().deshacer();
        if(!editor.texto().equals("Hola"))throw new AssertionError("Undo");
        System.out.println(editor.texto());
    }
}

/*
 * Salida esperada de consola:
 *   Hola
 * Después de ejecutar «Hola» y « mundo», deshacer el último comando
 * restaura el texto «Hola». Ninguna excepción debe propagarse.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
 *   java codetrace.practica.patrones.command.MainCommand
 * Salida esperada:
 *   Hola
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
