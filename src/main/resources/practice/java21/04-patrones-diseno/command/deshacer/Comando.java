// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
// Ejecutar: java codetrace.practica.patrones.command.MainCommand
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Editor de las notas de una ronda en un juego ficticio.
// Patrón command: Deshacer o rehacer acciones encapsuladas.
// Glosario: Ronda: segmento breve de una partida; deshacer: restaurar la nota anterior.

package codetrace.practica.patrones.command;

/**
 * Command es un objeto que puede ejecutarse y deshacerse.
 * Se preserva el estado anterior para implementar undo determinista.
 */
public interface Comando {
    void ejecutar();
    void deshacer();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AgregarTexto.java Comando.java Editor.java MainCommand.java
 *   java codetrace.practica.patrones.command.MainCommand
 * Nota: Comando.java es un componente; la salida corresponde al cliente MainCommand.java.
 * Salida esperada:
 *   Hola
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
