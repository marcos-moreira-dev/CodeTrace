// Tema:
// Un inner equijoin conserva todas las parejas con clave coincidente, incluso duplicados. Nested loop compara cada pareja O(n*m); Hash Join construye un índice de la derecha, coste esperado O(n+m+k) incluyendo k resultados. Ambos mantienen aquí el mismo orden.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 NestedLoopJoin.java HashJoin.java MainJoin.java
// Ejecutar: java MainJoin

public class MainJoin {
    public static void main(String[] args) throws Exception {
        String[][] productos = {{"1","pan"}, {"2","leche"}, {"3","arroz"}};
        String[][] ventas = {{"1","venta A"}, {"1","venta B"}, {"2","venta C"}, {"9","sin producto"}};
        var bucles = NestedLoopJoin.unir(productos, ventas);
        var hash = HashJoin.unir(productos, ventas);
        for (String fila : bucles) System.out.println(fila);
        System.out.println("Iguales: " + bucles.equals(hash));
    }
}

// Salida esperada:
// pan -> venta A
// pan -> venta B
// leche -> venta C
// Iguales: true
