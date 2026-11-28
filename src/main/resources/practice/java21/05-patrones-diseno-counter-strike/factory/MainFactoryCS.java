// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . FabricaMensaje.java MainFactoryCS.java MensajeChat.java MensajeSala.java
// Ejecutar: java codetrace.practica.counterstrike.factory.MainFactoryCS
// Dominio: Counter-Strike — chat de equipo: generar un aviso sin construir su implementación en el cliente.
// Patrón: Simple Factory; ejercicio educativo no oficial.
// Glosario: Chat = canal de mensajes de la partida; ronda = etapa breve del encuentro.
package codetrace.practica.counterstrike.factory;

/** Cliente: utiliza el contrato, no los detalles de construcción del chat. */
public final class MainFactoryCS {
    public static void main(String[] args) {
        MensajeSala aviso = FabricaMensaje.crearAvisoDeRonda("Ronda iniciada");
        // En otro cliente podría cambiarse el producto sin cambiar mostrar().
        System.out.println(aviso.mostrar());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . FabricaMensaje.java MainFactoryCS.java MensajeChat.java MensajeSala.java
 *   java codetrace.practica.counterstrike.factory.MainFactoryCS
 * Nota: MainFactoryCS.java pertenece al ejercicio; el cliente es MainFactoryCS.java.
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
