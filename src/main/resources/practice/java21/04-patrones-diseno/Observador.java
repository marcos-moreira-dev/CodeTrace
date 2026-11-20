// CodeTrace | Referencia: Java 21 LTS (Temurin JDK 21).
// Desde PowerShell en una COPIA de este paquete:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\06-patrones\Observador.java'
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
