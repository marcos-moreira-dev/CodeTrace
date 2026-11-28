// Tema:
// volatile garantiza el orden y la visibilidad de accesos a cada campo, pero dos lecturas
// separadas no son una fotografía atómica. Las barreras de coordinación de este ejemplo
// fuerzan una escritura entre ambas lecturas; así se observa la diferencia sin depender de
// una carrera aleatoria.
// Compilar: javac --release 21 -encoding UTF-8 -d . LecturasVolatile.java
// Ejecutar: java LecturasVolatile

public class LecturasVolatile {
    static volatile int primero, segundo;
    public static void main(String[] args) throws InterruptedException {
        var leido = new java.util.concurrent.CountDownLatch(1);
        var escrito = new java.util.concurrent.CountDownLatch(1);
        Thread escritor = new Thread(() -> {
            try {
                leido.await();
                primero++;
                segundo++;
                escrito.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        escritor.start();
        int a = primero;
        leido.countDown();
        escrito.await();
        int b = segundo;
        escritor.join();
        System.out.println(a + " " + b);
        System.out.println(primero + " " + segundo);
    }
}

// Salida esperada:
// 0 1
// 1 1
