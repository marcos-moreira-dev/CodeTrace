// Tema:
// El código de salida informa al padre del resultado. waitFor espera al proceso; onExit representa su finalización mediante un futuro.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

public class Hijo {
    public static void main(String[] args) throws Exception {
        System.exit(7);
    }
}

// Salida esperada:
// Codigo: 7
