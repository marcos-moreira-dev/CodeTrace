// Tema:
// Onion Architecture. Las reglas y el contrato de repositorio viven en el centro (dominio); la aplicación depende de ese centro y los detalles externos apuntan hacia dentro. Una cabaña se reserva para una sola visita en este modelo.
// Rol: Contrato interior de persistencia para las entidades del dominio.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Cabana.java dominio/RepositorioCabanas.java aplicacion/ReservarCabana.java infraestructura/CabanasEnMemoria.java presentacion/ConsolaCabanas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package dominio;

import java.util.Optional;

public interface RepositorioCabanas {
    Optional<Cabana> buscar(String nombre);
    void guardar(Cabana cabana);
}

// Salida esperada:
// Reservada: Mirador
// Reserva rechazada
