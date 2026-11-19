// CodeTrace | Referencia: Java 21, proyecto Observer
// Compilar: javac --release 21 -d out 07-proyectos/observer/*.java
package codetrace.observer;
public final class DemoObserver {
    public static void main(String[] args) {
        CentroEventos centro = new CentroEventos();
        centro.registrar(evento -> System.out.println(evento.mensaje()));
        centro.publicar(new Evento("Nueva venta"));
    }
}
