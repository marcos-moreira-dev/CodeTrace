// Tema:
// Onion Architecture. Las reglas y el contrato de repositorio viven en el centro (dominio); la aplicación depende de ese centro y los detalles externos apuntan hacia dentro. Una cabaña se reserva para una sola visita en este modelo.
// Rol: Entidad central; protege disponibilidad sin conocer la aplicación.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Cabana.java dominio/RepositorioCabanas.java aplicacion/ReservarCabana.java infraestructura/CabanasEnMemoria.java presentacion/ConsolaCabanas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package dominio;

public record Cabana(String nombre, boolean libre) {
    public Cabana reservar() {
        if (!libre) throw new IllegalStateException("Ocupada");
        return new Cabana(nombre, false);
    }
}

// Salida esperada:
// Reservada: Mirador
// Reserva rechazada
