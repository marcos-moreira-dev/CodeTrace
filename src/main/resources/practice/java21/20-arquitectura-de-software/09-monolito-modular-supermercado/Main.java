// Tema:
// Monolito modular. Compras y ventas son módulos dentro de una JVM; ambos consumen la API pública de inventario sin depender entre sí. La clase Existencias tiene acceso de paquete para ocultar los detalles internos.
// Rol: Una raíz de composición y un despliegue; módulos con límites explícitos.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build inventario/Existencias.java inventario/Inventario.java ventas/Ventas.java compras/Compras.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import inventario.Inventario;
import compras.Compras;
import ventas.Ventas;

public class Main {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Compras compras = new Compras(inventario);
        Ventas ventas = new Ventas(inventario);
        compras.comprar(10);
        ventas.vender(4);
        try { ventas.vender(7); }
        catch (IllegalArgumentException e) { System.out.println("Venta rechazada"); }
        System.out.println("Stock: " + inventario.stock());
    }
}

// Salida esperada:
// Compra: 10
// Venta: 4
// Venta rechazada
// Stock: 6
