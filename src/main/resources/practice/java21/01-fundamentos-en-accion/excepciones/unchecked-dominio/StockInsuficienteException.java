// Compilar (desde esta carpeta): javac -encoding UTF-8 Inventario.java MainExcepcionUnchecked.java StockInsuficienteException.java
// Ejecutar: java MainExcepcionUnchecked

/**
 * Excepción unchecked de una operación de inventario.
 *
 * Extender RuntimeException evita exigir catch/throws en cada llamador.
 * Puede ser apropiado para violaciones de contrato; el tipo concreto
 * comunica más que IllegalStateException cuando el dominio lo justifica.
 */
public final class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String sku, int pedido, int disponible) {
        super("Producto " + sku + ": solicitado " + pedido
                + ", disponible " + disponible);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Inventario.java MainExcepcionUnchecked.java StockInsuficienteException.java
 *   java MainExcepcionUnchecked
 * Nota: StockInsuficienteException.java es un componente; la salida corresponde al cliente MainExcepcionUnchecked.java.
 * Salida esperada:
 *   Rechazo controlado: Producto A-001: solicitado 10, disponible 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
