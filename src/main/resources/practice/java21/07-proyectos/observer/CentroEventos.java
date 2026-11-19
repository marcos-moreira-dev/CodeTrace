// CodeTrace | Referencia: Java 21, proyecto Observer
// Compilar: javac --release 21 -d out 07-proyectos/observer/*.java
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
