// Tema:
// Misma venta que 20/01. Arquitectura hexagonal: los puertos de aplicación controlan la dirección de dependencias. El adaptador de consola invoca Venta y el repositorio implementa Inventario.
// Rol: Puerto de salida requerido por el núcleo.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java aplicacion/puertos/Venta.java aplicacion/puertos/Inventario.java aplicacion/VenderVerdura.java infraestructura/ConsolaVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package aplicacion.puertos;

import dominio.Verdura;

public interface Inventario {
    Verdura leer();
    void guardar(Verdura verdura);
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
