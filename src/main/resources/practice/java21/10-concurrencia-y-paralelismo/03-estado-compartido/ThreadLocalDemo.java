// Tema:
// ThreadLocal conserva un valor por hilo, incluso si se comparte la misma instancia ThreadLocal. remove evita retención en trabajadores reutilizados.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ThreadLocalDemo.java
// Ejecutar: java ThreadLocalDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ThreadLocalDemo {
    static void esperar(Thread hilo) throws InterruptedException {
        hilo.join(3000);
        if (hilo.isAlive()) {
            hilo.interrupt();
            throw new IllegalStateException("Hilo no finalizado");
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadLocal<String> contexto = ThreadLocal.withInitial(() -> "vacio");
        contexto.set("main");
        String[] resultado = new String[1];
        Thread hilo = new Thread(() -> {
            contexto.set("trabajador");
            try { resultado[0] = contexto.get(); }
            finally { contexto.remove(); }
        });
        try {
            hilo.start();
            esperar(hilo);
            System.out.println(resultado[0]);
            System.out.println(contexto.get());
        } finally {
            contexto.remove();
        }
    }
}

// Salida esperada:
// trabajador
// main
