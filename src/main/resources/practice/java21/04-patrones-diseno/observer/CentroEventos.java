// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
package codetrace.observer;
import java.util.ArrayList;
import java.util.List;
public final class CentroEventos {
    private final List<Observador> observadores = new ArrayList<>();
    public void registrar(Observador observador) {
        observadores.add(observador);
    }
    public void publicar(Evento evento) {
        for (Observador observador : observadores)
            observador.actualizar(evento);
    }
}
