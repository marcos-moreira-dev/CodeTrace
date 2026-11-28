// Tema:
// Onion Architecture. Las reglas y el contrato de repositorio viven en el centro (dominio); la aplicación depende de ese centro y los detalles externos apuntan hacia dentro. Una cabaña se reserva para una sola visita en este modelo.
// Rol: Servicio de aplicación que orquesta la regla central mediante el contrato.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Cabana.java dominio/RepositorioCabanas.java aplicacion/ReservarCabana.java infraestructura/CabanasEnMemoria.java presentacion/ConsolaCabanas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package aplicacion;

import dominio.Cabana;
import dominio.RepositorioCabanas;

public class ReservarCabana {
    private final RepositorioCabanas repositorio;
    public ReservarCabana(RepositorioCabanas repositorio) { this.repositorio = repositorio; }

    public String ejecutar(String nombre) {
        Cabana cabana = repositorio.buscar(nombre)
            .orElseThrow(() -> new IllegalArgumentException("Cabana desconocida"));
        Cabana reservada = cabana.reservar();
        repositorio.guardar(reservada);
        // El servicio devuelve un dato de salida, no la entidad de dominio.
        return reservada.nombre();
    }
}

// Salida esperada:
// Reservada: Mirador
// Reserva rechazada
