// Tema:
// ProcessHandle identifica el proceso de la JVM actual. El PID lo asigna el sistema y no debe confundirse con el identificador de un hilo.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoActual.java
// Ejecutar: java ProcesoActual

public class ProcesoActual {
    public static void main(String[] args) throws Exception {
        ProcessHandle proceso = ProcessHandle.current();
        System.out.println("PID valido: " + (proceso.pid() > 0));
        System.out.println("Vivo: " + proceso.isAlive());
    }
}

// Salida esperada:
// PID valido: true
// Vivo: true
