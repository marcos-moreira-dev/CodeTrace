// Tema:
// Fork/Join divide recursivamente el problema hasta rangos pequeños, calcula una mitad directamente y combina con la otra mediante join. La clase tarea contiene su propio intervalo; un pool explícito limita trabajadores.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ForkJoinSuma.java
// Ejecutar: java ForkJoinSuma

import java.util.concurrent.*;

public class ForkJoinSuma extends RecursiveTask<Integer> {
    private final int[] datos;
    private final int desde, hasta;
    public ForkJoinSuma(int[] datos, int desde, int hasta) {
        this.datos = datos; this.desde = desde; this.hasta = hasta;
    }
    @Override protected Integer compute() {
        if (hasta - desde <= 2) {
            int total = 0;
            for (int i = desde; i < hasta; i++) total += datos[i];
            return total;
        }
        int medio = (desde + hasta) / 2;
        ForkJoinSuma izquierda = new ForkJoinSuma(datos, desde, medio);
        izquierda.fork();
        int derecha = new ForkJoinSuma(datos, medio, hasta).compute();
        return izquierda.join() + derecha;
    }
    public static void main(String[] args) throws Exception {
        int[] datos = {1, 2, 3, 4, 5, 6};
        ForkJoinPool pool = new ForkJoinPool(2);
        try {
            System.out.println(pool.submit(new ForkJoinSuma(datos, 0, datos.length)).get(3, TimeUnit.SECONDS));
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// 21
