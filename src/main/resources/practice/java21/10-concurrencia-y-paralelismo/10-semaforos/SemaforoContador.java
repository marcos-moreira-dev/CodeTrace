// Tema:
// Un semáforo contador representa capacidad disponible. Dos permisos admiten dos operaciones simultáneas; el tercero no obtiene permiso mientras estén ocupados.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 SemaforoContador.java
// Ejecutar: java SemaforoContador

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class SemaforoContador {
    public static void main(String[] args) throws Exception {
        Semaphore capacidad = new Semaphore(2);
        capacidad.acquire(2);
        try {
            System.out.println("Disponible: " + capacidad.availablePermits());
            System.out.println("Tercero: " + capacidad.tryAcquire());
        } finally { capacidad.release(2); }
        System.out.println("Disponible: " + capacidad.availablePermits());
    }
}

// Salida esperada:
// Disponible: 0
// Tercero: false
// Disponible: 2
