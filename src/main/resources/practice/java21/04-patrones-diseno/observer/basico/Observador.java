// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
// Sin main: compilación de clase reutilizable.

import java.util.ArrayList;
import java.util.List;

/**
 * Emisor Observer básico basado en una lista de interfaces funcionales.
 *
 * <p>No conoce las acciones ejecutadas por suscriptores, solo publica
 * el nuevo valor. En este ejercicio la lista no está sincronizada y
 * no hay desuscripción: en una aplicación real deben especificarse
 * esas políticas de ciclo de vida y manejo de excepciones.
 */
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainObservadorBasico
 * Nota: Observador.java es un componente; la salida corresponde al cliente MainObservadorBasico.java.
 * Salida esperada:
 *   consola=42 log=42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
