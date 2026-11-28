// Tema:
// Buffer circular de capacidad fija: libres cuenta huecos, ocupados cuenta datos, mutex protege índices. Si se interrumpe la espera del mutex se devuelve el permiso reservado; un centinela termina al consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 BufferCircular.java Productor.java Consumidor.java MainProductorConsumidor.java
// Ejecutar: java MainProductorConsumidor

public class Consumidor implements Runnable {
    private final BufferCircular buffer;
    public Consumidor(BufferCircular buffer) { this.buffer = buffer; }
    public void run() {
        try {
            int dato, suma = 0;
            while ((dato = buffer.sacar()) != -1) suma += dato;
            System.out.println("Suma: " + suma);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Suma: 10
