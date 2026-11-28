// Tema:
// Arquitectura hexagonal. Centro ecuestre reserva un caballo para una persona. El núcleo define puertos de entrada y salida; los adaptadores dependen de ellos. El dominio desconoce infraestructura.
// Rol: Puerto de salida: contrato que requiere la aplicación.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Caballo.java dominio/Reserva.java aplicacion/puertos/ServicioReservas.java aplicacion/puertos/RepositorioCaballos.java aplicacion/ReservarCaballo.java infraestructura/entrada/ConsolaReservas.java infraestructura/salida/RepositorioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package aplicacion.puertos;

import dominio.Caballo;
import java.util.Optional;

public interface RepositorioCaballos {
    Optional<Caballo> buscar(String id);
    void guardar(Caballo caballo);
}

// Salida esperada:
// Reserva: Luna para Ana
// Reserva rechazada
