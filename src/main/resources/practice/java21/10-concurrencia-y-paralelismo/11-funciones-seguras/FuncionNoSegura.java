// Tema:
// Una función que reutiliza un buffer mutable devuelve un alias compartido. Dos llamadas pueden alterar un resultado previo, incluso sin concurrencia.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 FuncionNoSegura.java
// Ejecutar: java FuncionNoSegura

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class FuncionNoSegura {
    static final int[] buffer = new int[1];
    static int[] calcular(int n) { buffer[0] = n * n; return buffer; }

    public static void main(String[] args) throws Exception {
        int[] primero = calcular(2);
        int[] segundo = calcular(5);
        System.out.println(primero[0] + " " + segundo[0]);
    }
}

// Salida esperada:
// 25 25
