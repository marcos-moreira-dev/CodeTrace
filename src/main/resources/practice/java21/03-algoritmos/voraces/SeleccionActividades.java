// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.actividades.SeleccionActividades

package codetrace.practica.actividades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Cliente de selección voraz de intervalos no solapados.
 *
 * <p>El algoritmo toma primero la actividad que termina antes y repite
 * con el próximo intervalo compatible. Para maximizar la CANTIDAD de
 * actividades, ese criterio admite prueba de intercambio; no debe
 * extenderse sin prueba a otros objetivos como ingresos ponderados.
 *
 * <p>Ordenar cuesta O(n log n), el barrido O(n) y la selección O(n)
 * memoria en este diseño. Los intervalos son semiabiertos [inicio, fin).
 */
public final class SeleccionActividades {
    record Intervalo(int inicio, int fin) {
        Intervalo {
            if (inicio >= fin) throw new IllegalArgumentException("Intervalo vacío");
        }
    }

    static List<Intervalo> seleccionar(Intervalo[] entrada) {
        Intervalo[] ordenados = entrada.clone();
        Arrays.sort(ordenados, Comparator.comparingInt(Intervalo::fin));
        List<Intervalo> salida = new ArrayList<>();
        int ultimoFin = Integer.MIN_VALUE;
        for (Intervalo actual : ordenados) {
            if (actual.inicio() >= ultimoFin) {
                salida.add(actual);
                ultimoFin = actual.fin();
            }
        }
        return List.copyOf(salida);
    }

    public static void main(String[] args) {
        Intervalo[] agenda = {new Intervalo(1, 4), new Intervalo(3, 5),
                new Intervalo(0, 6), new Intervalo(5, 7), new Intervalo(8, 9),
                new Intervalo(5, 9)};
        System.out.println(seleccionar(agenda));
    }
}
