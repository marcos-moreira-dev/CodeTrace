// Tema:
// Una máquina de estados finitos representa una sesión abierta o cerrada. La entrada determina una transición; una operación no permitida no modifica el estado.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -encoding UTF-8 MaquinaEstados.java
// Ejecutar: java MaquinaEstados

public class MaquinaEstados {
    private boolean abierta;
    public String recibir(String comando) {
        if (!abierta && comando.equals("ABRIR")) { abierta = true; return "OK"; }
        if (abierta && comando.equals("GUARDAR")) return "OK";
        if (abierta && comando.equals("CERRAR")) { abierta = false; return "OK"; }
        return "ERROR";
    }

    public static void main(String[] args) throws Exception {
        MaquinaEstados sesion = new MaquinaEstados();
        for (String comando : new String[]{"GUARDAR", "ABRIR", "GUARDAR", "CERRAR", "CERRAR"})
            System.out.println(comando + ": " + sesion.recibir(comando));
    }
}

// Salida esperada:
// GUARDAR: ERROR
// ABRIR: OK
// GUARDAR: OK
// CERRAR: OK
// CERRAR: ERROR
