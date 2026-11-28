// Tema:
// Arquitectura en capas. Una verdulería vende tomates; presentación llama al servicio, que utiliza la persistencia concreta. El dominio valida existencias. Las dependencias van hacia las capas inferiores.
// Rol: Raíz de composición: construye capas y prueba venta válida y rechazada.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java persistencia/InventarioEnMemoria.java servicio/ServicioVentas.java presentacion/ConsolaVentas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import persistencia.InventarioEnMemoria;
import servicio.ServicioVentas;
import presentacion.ConsolaVentas;

public class Main {
    public static void main(String[] args) {
        var consola = new ConsolaVentas(new ServicioVentas(new InventarioEnMemoria()));
        consola.vender(2);
        consola.vender(4);
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
