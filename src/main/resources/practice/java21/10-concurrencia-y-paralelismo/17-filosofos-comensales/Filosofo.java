// Tema:
// Cinco filósofos comparten cinco tenedores. Cada uno toma sus dos tenedores en orden creciente de identificador, rompiendo la espera circular. El resultado final no depende del orden de comida.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Mesa.java Filosofo.java MainFilosofos.java
// Ejecutar: java MainFilosofos

public class Filosofo implements Runnable {
    private final Mesa mesa;
    private final int id;
    public Filosofo(Mesa mesa, int id) { this.mesa = mesa; this.id = id; }
    public void run() {
        try { mesa.comer(id); }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Comidas: 5
