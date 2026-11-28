// Tema:
// Arquitectura hexagonal. Centro ecuestre reserva un caballo para una persona. El núcleo define puertos de entrada y salida; los adaptadores dependen de ellos. El dominio desconoce infraestructura.
// Rol: Adaptador conductor: llama al puerto de entrada.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Caballo.java dominio/Reserva.java aplicacion/puertos/ServicioReservas.java aplicacion/puertos/RepositorioCaballos.java aplicacion/ReservarCaballo.java infraestructura/entrada/ConsolaReservas.java infraestructura/salida/RepositorioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package infraestructura.entrada;

import aplicacion.puertos.ServicioReservas;

public class ConsolaReservas {
    private final ServicioReservas servicio;
    public ConsolaReservas(ServicioReservas servicio) { this.servicio = servicio; }
    public void reservar(String caballo, String cliente) {
        try {
            var r = servicio.reservar(caballo, cliente);
            System.out.println("Reserva: " + r.caballo() + " para " + r.cliente());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Reserva rechazada");
        }
    }
}

// Salida esperada:
// Reserva: Luna para Ana
// Reserva rechazada
