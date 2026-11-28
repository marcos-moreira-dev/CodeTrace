// Tema:
// Monolito modular. Compras y ventas son módulos dentro de una JVM; ambos consumen la API pública de inventario sin depender entre sí. La clase Existencias tiene acceso de paquete para ocultar los detalles internos.
// Rol: Módulo de compras; comparte inventario por inyección, no por variables globales.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build inventario/Existencias.java inventario/Inventario.java ventas/Ventas.java compras/Compras.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package compras;

import inventario.Inventario;

public class Compras {
    private final Inventario inventario;
    public Compras(Inventario inventario) { this.inventario = inventario; }
    public void comprar(int cantidad) {
        inventario.ingresar(cantidad);
        System.out.println("Compra: " + cantidad);
    }
}

// Salida esperada:
// Compra: 10
// Venta: 4
// Venta rechazada
// Stock: 6
