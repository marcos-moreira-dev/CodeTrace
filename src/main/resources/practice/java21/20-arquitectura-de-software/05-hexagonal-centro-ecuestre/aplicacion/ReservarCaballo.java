// Tema:
// Arquitectura hexagonal. Centro ecuestre reserva un caballo para una persona. El núcleo define puertos de entrada y salida; los adaptadores dependen de ellos. El dominio desconoce infraestructura.
// Rol: Caso de uso que implementa entrada y consume salida sin conocer adaptadores.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Caballo.java dominio/Reserva.java aplicacion/puertos/ServicioReservas.java aplicacion/puertos/RepositorioCaballos.java aplicacion/ReservarCaballo.java infraestructura/entrada/ConsolaReservas.java infraestructura/salida/RepositorioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package aplicacion;

import aplicacion.puertos.ServicioReservas;
import aplicacion.puertos.RepositorioCaballos;
import dominio.Caballo;
import dominio.Reserva;

public class ReservarCaballo implements ServicioReservas {
    private final RepositorioCaballos repositorio;
    public ReservarCaballo(RepositorioCaballos repositorio) { this.repositorio = repositorio; }
    public Reserva reservar(String id, String cliente) {
        Caballo caballo = repositorio.buscar(id).orElseThrow(() -> new IllegalArgumentException("Caballo"));
        Reserva reserva = new Reserva(id, cliente);
        repositorio.guardar(caballo.reservar());
        return reserva;
    }
}

// Salida esperada:
// Reserva: Luna para Ana
// Reserva rechazada
