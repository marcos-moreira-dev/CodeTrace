// Compilar (desde esta carpeta): javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
// Ejecutar: java MainFactoryBasica
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Comunicaciones del torneo: crear avisos para participantes.
// Patrón factory: Una fábrica devuelve un mensaje sin exponer el tipo concreto.
// Glosario: Torneo: competición entre varios equipos y partidas.
// Componente didáctico reutilizable: este archivo no tiene main.

/** Contrato independiente: la fábrica devuelve esta abstracción. */
public interface Mensaje {
    String enviar();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (práctica)
 * Compilar todos los .java desde la carpeta java21 y ejecutar MainFactoryBasica.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * Este tipo no tiene main; su cliente demuestra el comportamiento.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
 *   java MainFactoryBasica
 * Nota: Mensaje.java es un componente; la salida corresponde al cliente MainFactoryBasica.java.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
