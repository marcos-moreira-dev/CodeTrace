// Compilar (desde esta carpeta): javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
// Ejecutar: java MainFactoryBasica
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Correo de avisos de una competición ficticia por equipos.
// Patrón factory: Crear mensajes mediante un contrato en lugar de acoplar el cliente.
// Glosario: Torneo: competición que agrupa varias partidas.

/**
 * Fábrica simple de mensajes, no patrón Factory Method por herencia.
 *
 * <p>Su única responsabilidad es concentrar el conocimiento de la clase
 * concreta Correo y devolverla mediante el contrato Mensaje.
 * Ambos se definen en sus propios archivos para visualizar la arquitectura. El cliente
 * depende del contrato, no de la decisión de creación. La versión GoF
 * con creadores polimórficos está en factory-method/.
 */
public final class Factory {
    public static Mensaje crear(String destino) {
        return new Correo(destino);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Correo.java Factory.java MainFactoryBasica.java Mensaje.java
 *   java MainFactoryBasica
 * Nota: Factory.java es un componente; la salida corresponde al cliente MainFactoryBasica.java.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
