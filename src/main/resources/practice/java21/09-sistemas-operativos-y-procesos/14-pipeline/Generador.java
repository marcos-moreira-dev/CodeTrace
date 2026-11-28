// Tema:
// Una tubería conecta la salida de cada proceso con la entrada del siguiente. El generador emite números, el filtro conserva pares y el consumidor suma.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Generador.java Filtro.java Consumidor.java MainPipeline.java
// Ejecutar: java MainPipeline

public class Generador {
    public static void main(String[] args) throws Exception {
        for (int n = 1; n <= 5; n++) System.out.println(n);
    }
}

// Salida esperada:
// Suma: 6
