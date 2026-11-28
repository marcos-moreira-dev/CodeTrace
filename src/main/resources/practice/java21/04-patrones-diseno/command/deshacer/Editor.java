// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
// Ejecutar: java codetrace.practica.patrones.command.MainCommand
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Editor de las notas de una ronda en un juego ficticio.
// Patrón command: Deshacer o rehacer acciones encapsuladas.
// Glosario: Ronda: segmento breve de una partida; deshacer: restaurar la nota anterior.

package codetrace.practica.patrones.command;

/**
 * Receptor del patrón Command: contiene el estado del texto.
 * El comando no posee la UI, sino que encapsula una petición.
 */
public final class Editor {
    private final StringBuilder contenido=new StringBuilder();
    public int longitud(){return contenido.length();}
    public void agregar(String texto){contenido.append(texto);}
    public void borrarDesde(int inicio){contenido.setLength(inicio);}
    public String texto(){return contenido.toString();}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
 *   java codetrace.practica.patrones.command.MainCommand
 * Nota: Editor.java es un componente; la salida corresponde al cliente MainCommand.java.
 * Salida esperada:
 *   Hola
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
