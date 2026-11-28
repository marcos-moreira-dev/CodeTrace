// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" ComparadoresEncadenados
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Cliente de estudio para {@link Comparator} y las expresiones lambda.
 *
 * <p>Un comparador define el orden de dos objetos sin modificar su clase.
 * Primero se compara el precio de cada producto; si hay empate, se utiliza
 * su identificador. Esto es importante en árboles y ordenamientos: un
 * criterio secundario permite distinguir productos del mismo precio.
 *
 * <p>Se estudian interfaces funcionales, referencias de método, genéricos,
 * {@code comparingDouble}, {@code thenComparing} y la diferencia entre
 * ordenación y filtrado. La ordenación completa cuesta O(n log n) en el
 * caso habitual de la implementación utilizada por List.sort.
 */
public class ComparadoresEncadenados {
    record Producto(String codigo, String nombre, double precio) {}

    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>(List.of(
            new Producto("B", "Resistencia", 0.10),
            new Producto("A", "Sensor", 3.50),
            new Producto("C", "Cable", 0.10)
        ));
        Comparator<Producto> porPrecioYCodigo =
            Comparator.comparingDouble(Producto::precio)
                      .thenComparing(Producto::codigo);
        productos.sort(porPrecioYCodigo);
        productos.forEach(System.out::println);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out ComparadoresEncadenados
 * Salida esperada:
 *   Producto[codigo=B, nombre=Resistencia, precio=0.1]
 *   Producto[codigo=C, nombre=Cable, precio=0.1]
 *   Producto[codigo=A, nombre=Sensor, precio=3.5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
