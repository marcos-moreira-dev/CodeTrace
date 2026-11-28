// Tema:
// Buffer circular de capacidad fija: libres cuenta huecos, ocupados cuenta datos, mutex protege índices. Si se interrumpe la espera del mutex se devuelve el permiso reservado; un centinela termina al consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 BufferCircular.java Productor.java Consumidor.java MainProductorConsumidor.java
// Ejecutar: java MainProductorConsumidor

public class Productor implements Runnable {
    private final BufferCircular buffer;
    public Productor(BufferCircular buffer) { this.buffer = buffer; }
    public void run() {
        try {
            for (int n = 1; n <= 4; n++) buffer.poner(n);
            buffer.poner(-1); // Centinela fuera del dominio de datos.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Suma: 10
