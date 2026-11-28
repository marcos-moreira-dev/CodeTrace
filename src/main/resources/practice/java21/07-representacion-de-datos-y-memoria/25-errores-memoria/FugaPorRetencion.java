// Tema:
// Un objeto innecesario sigue alcanzable si una colección conserva su referencia. El
// ejemplo elimina las referencias retenidas; no agota memoria ni promete una recolección
// inmediata.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 FugaPorRetencion.java
// Ejecutar: java FugaPorRetencion

import java.util.ArrayList;

public class FugaPorRetencion {
    public static void main(String[] args) {
        ArrayList<byte[]> historial = new ArrayList<>();
        for (int i = 0; i < 3; i++) historial.add(new byte[1024]);
        System.out.println(historial.size());
        historial.clear();
        System.out.println(historial.size());
    }
}

// Salida esperada:
// 3
// 0
