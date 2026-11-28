// Tema:
// Una medición textual ficticia tiene tipo y valor; se valida la gramática antes de convertir. No se consulta un sensor.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 MedicionSimulada.java
// Ejecutar: java MedicionSimulada

public class MedicionSimulada {
    public static void main(String[] args) {
        String mensaje = "TEMP:25";
        if (!mensaje.matches("TEMP:[0-9]{1,2}")) throw new IllegalArgumentException("Medicion");
        int temperatura = Integer.parseInt(mensaje.substring(5));
        System.out.println("Temperatura: " + temperatura);
    }
}

// Salida esperada:
// Temperatura: 25
