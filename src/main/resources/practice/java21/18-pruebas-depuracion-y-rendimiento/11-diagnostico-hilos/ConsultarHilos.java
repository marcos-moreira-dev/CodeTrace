// Tema:
// ThreadMXBean permite consultar información de hilos de plataforma. Se observa el hilo principal por su identificador, sin imprimir todos los hilos ni asumir una cantidad fija.
// Modalidad: ejecución y modelo explícito.
// Compilar: javac --release 21 -encoding UTF-8 ConsultarHilos.java
// Ejecutar: java ConsultarHilos

import java.lang.management.*;

public class ConsultarHilos {
    public static void main(String[] args) throws Exception {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo info = bean.getThreadInfo(Thread.currentThread().threadId());
        System.out.println("Hilo encontrado: " + (info != null));
        System.out.println("Nombre coincide: " + (info != null && info.getThreadName().equals(Thread.currentThread().getName())));
        System.out.println("Sin deadlock detectado: " + (bean.findDeadlockedThreads() == null));
    }
}

// Salida esperada:
// Hilo encontrado: true
// Nombre coincide: true
// Sin deadlock detectado: true
