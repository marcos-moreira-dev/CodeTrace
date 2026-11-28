// Tema:
// jcmd y jstack inspeccionan una JVM hija creada por el propio programa. El hijo anuncia que está listo y espera una señal por stdin; las herramientas tienen plazo y archivos de salida temporales. Si attach no está disponible se informa explícitamente, no se simula un resultado exitoso.
// Modalidad: dependiente de herramientas y permisos del sistema.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoInspeccionable.java MainDiagnostico.java
// Ejecutar: java MainDiagnostico

public class ProcesoInspeccionable {
    public static void main(String[] args) throws Exception {
        System.out.println("LISTO");
        System.out.flush();
        System.in.read(); // El padre conserva stdin abierto hasta terminar.
    }
}

// Salida esperada:
// jcmd: OK
// jstack: OK
// Según disponibilidad: jcmd: OK; jstack: No disponible / jcmd: No disponible; jstack: OK / jcmd: No disponible; jstack: No disponible
