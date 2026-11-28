// Tema:
// Se inician tres hijos independientes con argumentos distintos. El orden de impresión del padre no representa el orden real de ejecución.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

public class Hijo {
    public static void main(String[] args) throws Exception {
        int n = Integer.parseInt(args[0]);
        System.out.println(n + " -> " + n * n);
    }
}

// Salida esperada:
// 1 -> 1
// 2 -> 4
// 3 -> 9
