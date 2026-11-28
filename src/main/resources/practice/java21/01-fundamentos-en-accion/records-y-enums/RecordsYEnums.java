// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Estado.java Pedido.java RecordsYEnums.java
// Ejecutar: java RecordsYEnums

import java.util.List;

/**
 * Cliente de dos construcciones muy habituales en Java moderno.
 * Estado.java y Pedido.java son tipos independientes con sus propios archivos.
 *
 * <p>Un enum representa un conjunto cerrado de valores con identidad estable.
 * Un record agrupa datos y genera constructor, accesores, equals, hashCode
 * y toString. Su inmutabilidad es superficial: un componente mutable
 * continúa siendo mutable, por lo que aquí se utiliza una String.
 *
 * <p>No todos los objetos deben ser records: una entidad mutable con reglas
 * de ciclo de vida puede justificar una clase tradicional.
 */
public class RecordsYEnums {
    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
            new Pedido("P-001", 12.5, Estado.RECIBIDO),
            new Pedido("P-002", 20, Estado.ENTREGADO)
        );
        pedidos.forEach(System.out::println);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Estado.java Pedido.java RecordsYEnums.java
 *   java RecordsYEnums
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
