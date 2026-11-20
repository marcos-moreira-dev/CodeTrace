// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.observer.MainObserver
package codetrace.observer;
/**
 * Cliente Observer: CentroEventos publica sin conocer los observadores
 * concretos. La lambda implementa una interfaz funcional que recibe
 * Evento; no se requiere una clase por cada suscriptor.
 *
 * Separa notificación del dominio frente a las reacciones del cliente.
 */
public final class MainObserver {
    public static void main(String[] args) {
        CentroEventos centro = new CentroEventos();
        centro.registrar(evento -> System.out.println(evento.mensaje()));
        centro.publicar(new Evento("Nueva venta"));
    }
}
