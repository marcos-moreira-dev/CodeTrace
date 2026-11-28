// Tema:
// Buffer circular de capacidad fija: libres cuenta huecos, ocupados cuenta datos, mutex protege índices. Si se interrumpe la espera del mutex se devuelve el permiso reservado; un centinela termina al consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 BufferCircular.java Productor.java Consumidor.java MainProductorConsumidor.java
// Ejecutar: java MainProductorConsumidor

import java.util.concurrent.Semaphore;

public class BufferCircular {
    private final int[] datos;
    private int entrada, salida;
    private final Semaphore libres, ocupados = new Semaphore(0), mutex = new Semaphore(1);

    public BufferCircular(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("Capacidad");
        datos = new int[capacidad];
        libres = new Semaphore(capacidad);
    }

    public void poner(int n) throws InterruptedException {
        libres.acquire();
        try { mutex.acquire(); }
        catch (InterruptedException e) { libres.release(); throw e; }
        try {
            datos[entrada] = n;
            entrada = (entrada + 1) % datos.length;
        } finally { mutex.release(); }
        ocupados.release();
    }

    public int sacar() throws InterruptedException {
        ocupados.acquire();
        try { mutex.acquire(); }
        catch (InterruptedException e) { ocupados.release(); throw e; }
        int n;
        try {
            n = datos[salida];
            salida = (salida + 1) % datos.length;
        } finally { mutex.release(); }
        libres.release();
        return n;
    }
}

// Salida esperada:
// Suma: 10
