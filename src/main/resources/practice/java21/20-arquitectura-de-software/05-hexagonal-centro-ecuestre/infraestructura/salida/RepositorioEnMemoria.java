// Tema:
// Arquitectura hexagonal. Centro ecuestre reserva un caballo para una persona. El núcleo define puertos de entrada y salida; los adaptadores dependen de ellos. El dominio desconoce infraestructura.
// Rol: Adaptador conducido: implementa el puerto de salida usando un mapa local.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Caballo.java dominio/Reserva.java aplicacion/puertos/ServicioReservas.java aplicacion/puertos/RepositorioCaballos.java aplicacion/ReservarCaballo.java infraestructura/entrada/ConsolaReservas.java infraestructura/salida/RepositorioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package infraestructura.salida;

import aplicacion.puertos.RepositorioCaballos;
import dominio.Caballo;
import java.util.HashMap;
import java.util.Optional;

public class RepositorioEnMemoria implements RepositorioCaballos {
    private final HashMap<String,Caballo> datos = new HashMap<>();
    public RepositorioEnMemoria() { guardar(new Caballo("Luna", true)); }
    public Optional<Caballo> buscar(String id) { return Optional.ofNullable(datos.get(id)); }
    public void guardar(Caballo caballo) { datos.put(caballo.id(), caballo); }
}

// Salida esperada:
// Reserva: Luna para Ana
// Reserva rechazada
