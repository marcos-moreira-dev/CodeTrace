// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.reinas.CuatroReinas

package codetrace.practica.reinas;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente del problema de las cuatro reinas mediante backtracking.
 *
 * <p>Una reina se coloca por fila. Antes de avanzar se comprueba que
 * ninguna previa comparta columna o diagonal; si hay conflicto, la
 * rama se poda. Al regresar de la recursión se deshace la elección.
 * Esta exploración es combinatoria, aunque la poda reduce casos reales.
 *
 * <p>La lista de soluciones copia el estado, porque el arreglo parcial
 * se reutiliza. Es una oportunidad para repasar recursión e invariantes.
 */
public final class CuatroReinas {
    private CuatroReinas() {}

    private static void resolver(int fila, int[] columnas, List<List<Integer>> soluciones) {
        if (fila == columnas.length) {
            List<Integer> solucion = new ArrayList<>();
            for (int columna : columnas) solucion.add(columna);
            soluciones.add(List.copyOf(solucion));
            return;
        }
        for (int candidata = 0; candidata < columnas.length; candidata++) {
            boolean valida = true;
            for (int f = 0; f < fila; f++) {
                if (columnas[f] == candidata
                    || Math.abs(columnas[f] - candidata) == fila - f) {
                    valida = false;
                    break;
                }
            }
            if (valida) {
                columnas[fila] = candidata;
                resolver(fila + 1, columnas, soluciones);
            }
        }
    }

    public static List<List<Integer>> soluciones(int n) {
        if (n < 1 || n > 12) throw new IllegalArgumentException("n entre 1 y 12");
        List<List<Integer>> resultado = new ArrayList<>();
        resolver(0, new int[n], resultado);
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("4 reinas: " + soluciones(4));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.reinas.CuatroReinas
 * Salida esperada:
 *   4 reinas: [[1, 3, 0, 2], [2, 0, 3, 1]]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
