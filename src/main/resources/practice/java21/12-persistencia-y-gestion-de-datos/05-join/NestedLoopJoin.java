// Tema:
// Un inner equijoin conserva todas las parejas con clave coincidente, incluso duplicados. Nested loop compara cada pareja O(n*m); Hash Join construye un índice de la derecha, coste esperado O(n+m+k) incluyendo k resultados. Ambos mantienen aquí el mismo orden.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 NestedLoopJoin.java HashJoin.java MainJoin.java
// Ejecutar: java MainJoin

import java.util.ArrayList;
import java.util.List;

public class NestedLoopJoin {
    public static List<String> unir(String[][] izquierda, String[][] derecha) {
        List<String> resultado = new ArrayList<>();
        for (String[] a : izquierda)
            for (String[] b : derecha)
                if (a[0].equals(b[0])) resultado.add(a[1] + " -> " + b[1]);
        return resultado;
    }
}

// Salida esperada:
// pan -> venta A
// pan -> venta B
// leche -> venta C
// Iguales: true
