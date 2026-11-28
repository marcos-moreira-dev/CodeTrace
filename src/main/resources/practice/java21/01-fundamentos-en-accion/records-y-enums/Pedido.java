// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Estado.java Pedido.java RecordsYEnums.java
// Ejecutar: java RecordsYEnums
// Componente didáctico reutilizable: este archivo no tiene main.

/** Valor inmutable superficialmente; los componentes son String, double y Estado. */
public record Pedido(String codigo, double total, Estado estado) {
    public Pedido {
        if (codigo == null || codigo.isBlank() || total < 0)
            throw new IllegalArgumentException("Pedido inválido");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (práctica)
 * Compilar todos los .java desde la carpeta java21 y ejecutar RecordsYEnums.
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 * Este tipo no tiene main; su cliente demuestra el comportamiento.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Estado.java Pedido.java RecordsYEnums.java
 *   java RecordsYEnums
 * Nota: Pedido.java es un componente; la salida corresponde al cliente RecordsYEnums.java.
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
