// Tema:
// El padre puede no estar disponible si terminó o si el sistema limita la información. Se trata Optional de manera explícita.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoPadre.java
// Ejecutar: java ProcesoPadre

public class ProcesoPadre {
    public static void main(String[] args) throws Exception {
        ProcessHandle actual = ProcessHandle.current();
        if (actual.parent().isPresent()) {
            System.out.println("Padre distinto: " + (actual.parent().get().pid() != actual.pid()));
        } else {
            System.out.println("Padre no disponible");
        }
    }
}

// Salida esperada:
// Padre distinto: true
// También puede observarse: Padre no disponible
