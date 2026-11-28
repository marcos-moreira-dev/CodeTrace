// Tema:
// Un inner equijoin conserva todas las parejas con clave coincidente, incluso duplicados. Nested loop compara cada pareja O(n*m); Hash Join construye un índice de la derecha, coste esperado O(n+m+k) incluyendo k resultados. Ambos mantienen aquí el mismo orden.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 NestedLoopJoin.java HashJoin.java MainJoin.java
// Ejecutar: java MainJoin

import java.util.*;

public class HashJoin {
    public static List<String> unir(String[][] izquierda, String[][] derecha) {
        Map<String, List<String>> indice = new HashMap<>();
        for (String[] b : derecha)
            indice.computeIfAbsent(b[0], k -> new ArrayList<>()).add(b[1]);
        List<String> resultado = new ArrayList<>();
        for (String[] a : izquierda) {
            List<String> coincidencias = indice.get(a[0]);
            if (coincidencias != null)
                for (String b : coincidencias) resultado.add(a[1] + " -> " + b);
        }
        return resultado;
    }
}

// Salida esperada:
// pan -> venta A
// pan -> venta B
// leche -> venta C
// Iguales: true
