// Tema:
// Arquitectura hexagonal. Centro ecuestre reserva un caballo para una persona. El núcleo define puertos de entrada y salida; los adaptadores dependen de ellos. El dominio desconoce infraestructura.
// Rol: Composición externa; inyecta el adaptador de repositorio en el núcleo.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Caballo.java dominio/Reserva.java aplicacion/puertos/ServicioReservas.java aplicacion/puertos/RepositorioCaballos.java aplicacion/ReservarCaballo.java infraestructura/entrada/ConsolaReservas.java infraestructura/salida/RepositorioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import aplicacion.ReservarCaballo;
import infraestructura.entrada.ConsolaReservas;
import infraestructura.salida.RepositorioEnMemoria;

public class Main {
    public static void main(String[] args) {
        var consola = new ConsolaReservas(new ReservarCaballo(new RepositorioEnMemoria()));
        consola.reservar("Luna", "Ana");
        consola.reservar("Luna", "Luis");
    }
}

// Salida esperada:
// Reserva: Luna para Ana
// Reserva rechazada
