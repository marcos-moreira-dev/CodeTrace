// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Marcador de ronda que notifica resultados a los espectadores.
// Patrón observer: Varios observadores reciben cambios sin ser consultados continuamente.
// Glosario: Ronda: segmento breve de una partida; marcador: registro de resultados.
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
