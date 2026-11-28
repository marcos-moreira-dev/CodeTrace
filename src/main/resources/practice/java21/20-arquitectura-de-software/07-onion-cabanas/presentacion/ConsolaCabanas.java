// Tema:
// Onion Architecture. Las reglas y el contrato de repositorio viven en el centro (dominio); la aplicación depende de ese centro y los detalles externos apuntan hacia dentro. Una cabaña se reserva para una sola visita en este modelo.
// Rol: Anillo externo que llama al servicio y presenta el resultado.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Cabana.java dominio/RepositorioCabanas.java aplicacion/ReservarCabana.java infraestructura/CabanasEnMemoria.java presentacion/ConsolaCabanas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package presentacion;

import aplicacion.ReservarCabana;

public class ConsolaCabanas {
    private final ReservarCabana servicio;
    public ConsolaCabanas(ReservarCabana servicio) { this.servicio = servicio; }
    public void reservar(String nombre) {
        try {
            System.out.println("Reservada: " + servicio.ejecutar(nombre));
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Reserva rechazada");
        }
    }
}

// Salida esperada:
// Reservada: Mirador
// Reserva rechazada
