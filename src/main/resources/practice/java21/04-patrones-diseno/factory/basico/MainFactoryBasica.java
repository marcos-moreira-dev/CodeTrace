// Compilar (desde esta carpeta): javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
// Ejecutar: java MainFactoryBasica
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Comunicaciones del torneo: crear avisos para participantes.
// Patrón factory: Una fábrica devuelve un mensaje sin exponer el tipo concreto.
// Glosario: Torneo: competición entre varios equipos y partidas.

/**
 * Cliente de una fábrica simple, distinto de Factory Method GoF.
 *
 * <p>Una fábrica simple concentra la creación de un objeto para que
 * otros consumidores conozcan el contrato Mensaje, pero no construyan
 * la implementación concreta Correo. No es obligatorio llamarla
 * Factory Method: en ese patrón la creación se delega habitualmente
 * a subclases de una abstracción creadora.
 *
 * <p>Mensaje.java enseña el contrato; Correo.java, el record; Factory.java, la creación.
 * Aquí solo invocamos y presentamos el resultado, manteniendo el
 * foco en la responsabilidad de cada archivo. La relación queda
 * visible también en el mapa de dependencias de CodeTrace.
 */
public final class MainFactoryBasica {
    public static void main(String[] args) {
        Mensaje mensaje = Factory.crear("cliente@example.invalid");
        System.out.println(mensaje.enviar());
    }
}

/*
 * Salida esperada de consola:
 *   Correo a cliente@example.invalid
 * El cliente recibe el contrato Mensaje sin construir Correo directamente.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
 *   java MainFactoryBasica
 * Salida esperada:
 *   Correo a cliente@example.invalid
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
