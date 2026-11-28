// Tema:
// El padre puede no estar disponible si terminó o si el sistema limita la información. Se trata Optional de manera explícita.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoPadre.java
// Ejecutar: java ProcesoPadre

public class ProcesoPadre {
    public static void main(String[] args) throws Exception {
        ProcessHandle actual = ProcessHandle.current();
        var padre = actual.parent();
        if (padre.isPresent()) {
            System.out.println("Padre distinto: " + (padre.get().pid() != actual.pid()));
        } else {
            System.out.println("Padre no disponible");
        }
    }
}

// Salida esperada:
// Padre distinto: true
// También puede observarse: Padre no disponible
