// Tema:
// Modelo de estados de un proceso: listo, ejecución, bloqueado y terminado. La transición de bloqueado a listo representa que llegó un evento.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 EstadosProceso.java
// Ejecutar: java EstadosProceso

public class EstadosProceso {
    public static void main(String[] args) throws Exception {
        String estado = "LISTO";
        System.out.println(estado);
        estado = "EJECUCION";
        System.out.println(estado);
        estado = "BLOQUEADO";
        System.out.println(estado);
        estado = "LISTO";
        System.out.println(estado);
        estado = "EJECUCION";
        System.out.println(estado);
        estado = "TERMINADO";
        System.out.println(estado);
    }
}

// Salida esperada:
// LISTO
// EJECUCION
// BLOQUEADO
// LISTO
// EJECUCION
// TERMINADO
