// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.patrones.command.MainCommand

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
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
