// Tema:
// Onion Architecture. Las reglas y el contrato de repositorio viven en el centro (dominio); la aplicación depende de ese centro y los detalles externos apuntan hacia dentro. Una cabaña se reserva para una sola visita en este modelo.
// Rol: Punto de composición externo: conecta infraestructura con aplicación.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Cabana.java dominio/RepositorioCabanas.java aplicacion/ReservarCabana.java infraestructura/CabanasEnMemoria.java presentacion/ConsolaCabanas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import aplicacion.ReservarCabana;
import infraestructura.CabanasEnMemoria;
import presentacion.ConsolaCabanas;

public class Main {
    public static void main(String[] args) {
        var consola = new ConsolaCabanas(new ReservarCabana(new CabanasEnMemoria()));
        consola.reservar("Mirador");
        consola.reservar("Mirador");
    }
}

// Salida esperada:
// Reservada: Mirador
// Reserva rechazada
