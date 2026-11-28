// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
// Ejecutar: java MainFactoryBasica
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Correo de avisos de una competición ficticia por equipos.
// Patrón factory: Crear mensajes mediante un contrato en lugar de acoplar el cliente.
// Glosario: Torneo: competición que agrupa varias partidas.
// Componente didáctico reutilizable: este archivo no tiene main.

/** Implementación concreta de Mensaje mediante un record de Java 21. */
public record Correo(String destino) implements Mensaje {
    @Override public String enviar() {
        return "Correo a " + destino;
    }
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
 * Nota: Correo.java es un componente; la salida corresponde al cliente MainFactoryBasica.java.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
