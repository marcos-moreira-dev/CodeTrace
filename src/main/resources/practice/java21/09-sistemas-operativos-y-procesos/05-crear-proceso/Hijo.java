// Tema:
// ProcessBuilder inicia un nuevo programa; aquí una JVM hija, no una copia de la memoria del padre como fork. El padre recoge una salida pequeña.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

public class Hijo {
    public static void main(String[] args) throws Exception {
        System.out.println("Hola desde el hijo");
    }
}

// Salida esperada:
// Hola desde el hijo
// Hijo finalizado
