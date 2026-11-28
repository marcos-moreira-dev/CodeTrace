// Tema:
// La JVM ejecuta bytecode sobre un sistema operativo. Sus propiedades describen el entorno, no los registros físicos del procesador.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 EntornoSistema.java
// Ejecutar: java EntornoSistema

public class EntornoSistema {
    public static void main(String[] args) throws Exception {
        System.out.println("SO identificado: " + !System.getProperty("os.name", "").isBlank());
        System.out.println("Arquitectura identificada: " + !System.getProperty("os.arch", "").isBlank());
        System.out.println("Java 21 o superior: " + (Runtime.version().feature() >= 21));
    }
}

// Salida esperada:
// SO identificado: true
// Arquitectura identificada: true
// Java 21 o superior: true
