// CodeTrace | Referencia: Java 21, proyecto Observer
// Compilar: javac --release 21 -d out 07-proyectos/observer/*.java
package codetrace.observer;
public interface Observador {
    void actualizar(Evento evento);
}
