// Tema:
// waitFor con límite evita esperar indefinidamente. El padre termina exclusivamente el hijo que creó, nunca procesos ajenos.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

public class Hijo {
    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
    }
}

// Salida esperada:
// Tiempo agotado
// Hijo vivo: false
