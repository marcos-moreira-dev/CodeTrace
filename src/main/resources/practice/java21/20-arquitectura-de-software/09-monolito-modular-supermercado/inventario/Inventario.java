// Tema:
// Monolito modular. Compras y ventas son módulos dentro de una JVM; ambos consumen la API pública de inventario sin depender entre sí. La clase Existencias tiene acceso de paquete para ocultar los detalles internos.
// Rol: Fachada pública del módulo, única vía para cambiar sus existencias.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build inventario/Existencias.java inventario/Inventario.java ventas/Ventas.java compras/Compras.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package inventario;

public class Inventario {
    private final Existencias existencias = new Existencias();
    public void ingresar(int n) { existencias.agregar(n); }
    public void descontar(int n) { existencias.retirar(n); }
    public int stock() { return existencias.cantidad(); }
}

// Salida esperada:
// Compra: 10
// Venta: 4
// Venta rechazada
// Stock: 6
