// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 StreamsYFiltros.java
// Ejecutar: java StreamsYFiltros
import java.util.List;

/**
 * Cliente introductorio de la API Stream.
 *
 * <p>Un stream describe una secuencia de transformaciones sobre datos;
 * {@code filter} mantiene elementos que cumplen un predicado, {@code map}
 * transforma valores y {@code toList} materializa el resultado.
 *
 * <p>La fuente original no cambia y la evaluación de un pipeline es
 * diferida hasta su operación terminal. Un recorrido simple cuesta O(n).
 * Un stream no es una estructura de datos y no implica paralelismo.
 */
public class StreamsYFiltros {
    record Producto(String nombre, double precio) {}

    public static void main(String[] args) {
        List<Producto> productos = List.of(
            new Producto("Sensor", 7.5),
            new Producto("Cable", 1.2),
            new Producto("Placa", 12.0)
        );
        List<String> economicos = productos.stream()
            .filter(p -> p.precio() < 10.0)
            .map(Producto::nombre)
            .toList();
        System.out.println("Productos económicos: " + economicos);
        System.out.println("Fuente original: " + productos.size());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 StreamsYFiltros.java
 *   java StreamsYFiltros
 * Salida esperada:
 *   Productos económicos: [Sensor, Cable]
 *   Fuente original: 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
