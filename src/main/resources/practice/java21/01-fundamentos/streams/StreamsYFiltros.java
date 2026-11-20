// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" StreamsYFiltros
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
