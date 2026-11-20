// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
// Sin main: compilación de clase reutilizable.

import java.util.ArrayList;
import java.util.List;

public final class Observador {
    @FunctionalInterface
    public interface Escucha {
        void alCambiar(int nuevoValor);
    }

    private final List<Escucha> oyentes = new ArrayList<>();
    private int valor;

    public void suscribir(Escucha escucha) {
        oyentes.add(escucha);
    }

    public void actualizar(int nuevoValor) {
        valor = nuevoValor;
        for (Escucha escucha : oyentes) escucha.alCambiar(valor);
    }

    public int valor() {
        return valor;
    }
}
