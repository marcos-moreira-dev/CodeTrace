// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.observer.DemoObserver
package codetrace.observer;
public final class DemoObserver {
    public static void main(String[] args) {
        CentroEventos centro = new CentroEventos();
        centro.registrar(evento -> System.out.println(evento.mensaje()));
        centro.publicar(new Evento("Nueva venta"));
    }
}
