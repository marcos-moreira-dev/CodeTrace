// Tema:
// CAS compara el valor observado y reemplaza solo si todavía coincide. Si otro cambió el dato, el intento falla.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CompareAndSet.java
// Ejecutar: java CompareAndSet

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CompareAndSet {
    public static void main(String[] args) throws Exception {
        AtomicInteger saldo = new AtomicInteger(10);
        System.out.println(saldo.compareAndSet(10, 15));
        System.out.println(saldo.compareAndSet(10, 20));
        System.out.println(saldo.get());
    }
}

// Salida esperada:
// true
// false
// 15
