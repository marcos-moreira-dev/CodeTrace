// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Inventario.java MainExcepcionUnchecked.java StockInsuficienteException.java
// Ejecutar: java MainExcepcionUnchecked

/** Modelo mínimo de inventario: unidades nunca negativas. */
public final class Inventario {
    private final String sku;
    private int unidades;

    public Inventario(String sku, int unidades) {
        if (sku == null || sku.isBlank() || unidades < 0)
            throw new IllegalArgumentException("Datos de inventario");
        this.sku = sku;
        this.unidades = unidades;
    }

    public void despachar(int solicitadas) {
        if (solicitadas <= 0) throw new IllegalArgumentException("Cantidad positiva");
        if (solicitadas > unidades)
            throw new StockInsuficienteException(sku, solicitadas, unidades);
        unidades -= solicitadas;
    }
    public int disponibles() { return unidades; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Inventario.java MainExcepcionUnchecked.java StockInsuficienteException.java
 *   java MainExcepcionUnchecked
 * Nota: Inventario.java es un componente; la salida corresponde al cliente MainExcepcionUnchecked.java.
 * Salida esperada:
 *   Rechazo controlado: Producto A-001: solicitado 10, disponible 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
