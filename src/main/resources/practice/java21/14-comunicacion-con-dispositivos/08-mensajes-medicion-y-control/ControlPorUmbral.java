// Tema:
// Un controlador lógico decide un comando según un umbral. Aquí solo genera texto a partir de mediciones de prueba, sin activar dispositivos.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 ControlPorUmbral.java
// Ejecutar: java ControlPorUmbral

public class ControlPorUmbral {
    static String comando(int temperatura) {
        return temperatura >= 28 ? "ENCENDER" : "APAGAR";
    }

    public static void main(String[] args) {
        for (int temperatura : new int[]{24, 30, 28})
            System.out.println(temperatura + ": " + comando(temperatura));
    }
}

// Salida esperada:
// 24: APAGAR
// 30: ENCENDER
// 28: ENCENDER
