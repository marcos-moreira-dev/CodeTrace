// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

package codetrace.practica.patrones.composite;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.composite.MainComposite; salida esperada: Bytes: 220. */

import java.util.ArrayList;
import java.util.List;

/** Componente compuesto que suma recursivamente el tamaño de sus hijos. */
public final class Carpeta implements Elemento {
    private final String nombre;
    private final List<Elemento> hijos = new ArrayList<>();

    public Carpeta(String nombre) { this.nombre = nombre; }

    public void agregar(Elemento elemento) {
        if (elemento == this) throw new IllegalArgumentException("Ciclo directo");
        hijos.add(elemento);
    }

    @Override public int bytes() {
        return hijos.stream().mapToInt(Elemento::bytes).sum();
    }

    public String nombre() { return nombre; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.composite.MainComposite
 * Nota: Carpeta.java es un componente; la salida corresponde al cliente MainComposite.java.
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
