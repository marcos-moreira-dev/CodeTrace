// Tema:
// Un shutdown hook se ejecuta durante una terminación ordenada de la JVM, no necesariamente tras una interrupción abrupta. El padre observa el cierre normal.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 HijoConShutdownHook.java Padre.java
// Ejecutar: java Padre

public class HijoConShutdownHook {
    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Liberar recursos")));
        System.out.println("Trabajo completo");
    }
}

// Salida esperada:
// Trabajo completo
// Liberar recursos
