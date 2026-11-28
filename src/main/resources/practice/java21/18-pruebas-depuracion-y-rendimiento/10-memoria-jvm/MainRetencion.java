// Tema:
// Una colección mantiene referencias fuertes mientras contiene objetos. Limpiarla elimina esas referencias, pero no obliga al GC a recuperar memoria de inmediato; se comprueba el número de referencias retenidas.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 MainRetencion.java
// Ejecutar: java MainRetencion

import java.util.ArrayList;
import java.util.List;

public class MainRetencion {
    public static void main(String[] args) throws Exception {
        List<byte[]> cache = new ArrayList<>();
        for (int i = 0; i < 4; i++) cache.add(new byte[1024]);
        System.out.println("Referencias: " + cache.size());
        cache.clear();
        System.out.println("Referencias: " + cache.size());
    }
}

// Salida esperada:
// Referencias: 4
// Referencias: 0
