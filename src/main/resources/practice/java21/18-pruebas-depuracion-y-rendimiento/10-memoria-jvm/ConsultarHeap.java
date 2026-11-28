// Tema:
// MemoryMXBean informa uso y compromiso del heap, no direcciones físicas ni tamaño exacto de cada objeto. Solo se verifican relaciones documentadas, no valores que varían por JVM.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 ConsultarHeap.java
// Ejecutar: java ConsultarHeap

import java.lang.management.*;

public class ConsultarHeap {
    public static void main(String[] args) throws Exception {
        MemoryUsage heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        System.out.println("Usado no negativo: " + (heap.getUsed() >= 0));
        System.out.println("Comprometido cubre uso: " + (heap.getCommitted() >= heap.getUsed()));
    }
}

// Salida esperada:
// Usado no negativo: true
// Comprometido cubre uso: true
