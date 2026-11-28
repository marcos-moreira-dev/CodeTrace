// Tema:
// Onion Architecture. Las reglas y el contrato de repositorio viven en el centro (dominio); la aplicación depende de ese centro y los detalles externos apuntan hacia dentro. Una cabaña se reserva para una sola visita en este modelo.
// Rol: Anillo externo: implementa el contrato de dominio.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Cabana.java dominio/RepositorioCabanas.java aplicacion/ReservarCabana.java infraestructura/CabanasEnMemoria.java presentacion/ConsolaCabanas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package infraestructura;

import dominio.Cabana;
import dominio.RepositorioCabanas;
import java.util.HashMap;
import java.util.Optional;

public class CabanasEnMemoria implements RepositorioCabanas {
    private final HashMap<String,Cabana> datos = new HashMap<>();
    public CabanasEnMemoria() { guardar(new Cabana("Mirador", true)); }
    public Optional<Cabana> buscar(String nombre) { return Optional.ofNullable(datos.get(nombre)); }
    public void guardar(Cabana cabana) { datos.put(cabana.nombre(), cabana); }
}

// Salida esperada:
// Reservada: Mirador
// Reserva rechazada
