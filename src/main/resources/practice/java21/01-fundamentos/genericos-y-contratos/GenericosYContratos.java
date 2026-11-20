// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" GenericosYContratos

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente de genéricos, límites y comodines.
 *
 * <p>T limita los valores aceptados y elimina conversiones innecesarias.
 * Un productor puede exponerse como List<? extends Number> para lectura;
 * un receptor List<? super Integer> admite insertar Integer. Esto
 * ejemplifica PECS (Producer Extends, Consumer Super), sin desarrollar
 * una jerarquía que no ayude al ejemplo.
 */
public class GenericosYContratos {
    static double sumar(List<? extends Number> fuente) {
        double total = 0;
        for (Number n : fuente) total += n.doubleValue();
        return total;
    }

    static void agregarEnteros(List<? super Integer> destino, int... valores) {
        for (int valor : valores) destino.add(valor);
    }

    public static void main(String[] args) {
        List<Number> numeros = new ArrayList<>();
        agregarEnteros(numeros, 2, 3, 5);
        System.out.println("Total: " + sumar(numeros));
    }
}
