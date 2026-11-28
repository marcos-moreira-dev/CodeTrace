// Tema:
// Dos transacciones leen el mismo valor y calculan nuevas cantidades. La segunda sobrescribe la primera. Una actualización atómica, bloqueo o comprobación de versión puede evitarlo, según el motor.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 PerdidaActualizacion.java
// Ejecutar: java PerdidaActualizacion

public class PerdidaActualizacion {
    public static void main(String[] args) throws Exception {
        int cantidad = 10;
        int lecturaA = cantidad, lecturaB = cantidad;
        cantidad = lecturaA + 2;
        cantidad = lecturaB + 3;
        System.out.println("Esperado: 15");
        System.out.println("Observado: " + cantidad);
    }
}

// Salida esperada:
// Esperado: 15
// Observado: 13
