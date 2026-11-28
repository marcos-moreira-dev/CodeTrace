// Tema:
// ByteOrder.nativeOrder informa el orden nativo de la plataforma. Para formatos de archivo
// o protocolos se elige el orden explícitamente.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 OrdenNativo.java
// Ejecutar: java OrdenNativo

import java.nio.ByteOrder;

public class OrdenNativo {
    public static void main(String[] args) {
        System.out.println(ByteOrder.nativeOrder());
    }
}

// Salida esperada:
// Resultados permitidos: LITTLE_ENDIAN o BIG_ENDIAN
