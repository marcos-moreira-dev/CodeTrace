// Tema:
// Misma venta que 20/01. Arquitectura hexagonal: los puertos de aplicación controlan la dirección de dependencias. El adaptador de consola invoca Venta y el repositorio implementa Inventario.
// Rol: Adaptador de salida; cumple la interfaz requerida por aplicación.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java aplicacion/puertos/Venta.java aplicacion/puertos/Inventario.java aplicacion/VenderVerdura.java infraestructura/ConsolaVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package infraestructura;

import aplicacion.puertos.Inventario;
import dominio.Verdura;

public class InventarioEnMemoria implements Inventario {
    private Verdura verdura = new Verdura("tomate", 5);
    public Verdura leer() { return verdura; }
    public void guardar(Verdura nueva) { verdura = nueva; }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
