// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
// Ejecutar: java codetrace.practica.patrones.command.MainCommand
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Editor de las notas de una ronda en un juego ficticio.
// Patrón command: Deshacer o rehacer acciones encapsuladas.
// Glosario: Ronda: segmento breve de una partida; deshacer: restaurar la nota anterior.
// Este archivo define exactamente un tipo reutilizable, sin main.

package codetrace.practica.patrones.command;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.command.MainCommand; salida esperada: Hola. */

/** Comando concreto que guarda el estado anterior para deshacer. */
public final class AgregarTexto implements Comando {
    private final Editor receptor;
    private final String texto;
    private int longitudAnterior=-1;
    public AgregarTexto(Editor receptor,String texto){
        this.receptor=receptor;this.texto=texto;
    }
    public void ejecutar(){
        if(longitudAnterior>=0)throw new IllegalStateException("Ya ejecutado");
        longitudAnterior=receptor.longitud();
        receptor.agregar(texto);
    }
    public void deshacer(){
        if(longitudAnterior<0)throw new IllegalStateException("Sin ejecución");
        receptor.borrarDesde(longitudAnterior);
        longitudAnterior=-1;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
 *   java codetrace.practica.patrones.command.MainCommand
 * Nota: AgregarTexto.java es un componente; la salida corresponde al cliente MainCommand.java.
 * Salida esperada:
 *   Hola
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
