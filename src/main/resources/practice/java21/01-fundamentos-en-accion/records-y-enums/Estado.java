// Compilar (desde esta carpeta): javac -encoding UTF-8 Estado.java Pedido.java RecordsYEnums.java
// Ejecutar: java RecordsYEnums
// Componente didáctico reutilizable: este archivo no tiene main.

/** Conjunto cerrado de estados posibles de un pedido. */
public enum Estado {
    RECIBIDO, PREPARANDO, ENTREGADO
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
 * Nota: Estado.java es un componente; la salida corresponde al cliente RecordsYEnums.java.
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
