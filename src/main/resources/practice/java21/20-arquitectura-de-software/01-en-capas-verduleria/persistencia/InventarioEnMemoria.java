// Tema:
// Arquitectura en capas. Una verdulería vende tomates; presentación llama al servicio, que utiliza la persistencia concreta. El dominio valida existencias. Las dependencias van hacia las capas inferiores.
// Rol: Almacena el estado de la verdulería; no decide cómo vender.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java persistencia/InventarioEnMemoria.java servicio/ServicioVentas.java presentacion/ConsolaVentas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package persistencia;

import dominio.Verdura;

public class InventarioEnMemoria {
    private Verdura verdura = new Verdura("tomate", 5);

    public Verdura leer() { return verdura; }
    public void guardar(Verdura nueva) { verdura = nueva; }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
