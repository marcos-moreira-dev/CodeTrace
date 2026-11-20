// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.patrones.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite representa uniformemente hojas y grupos de objetos.
 * La carpeta agrega archivos/carpetas; el archivo es una hoja.
 * Cada tamaño se calcula de forma recursiva O(n), n nodos.
 * No son archivos reales del disco: modelo puro de consola.
 */
public sealed interface Elemento permits Elemento.Archivo, Elemento.Carpeta {
    int bytes();
    record Archivo(String nombre,int bytes) implements Elemento {
        public Archivo { if(bytes<0)throw new IllegalArgumentException("bytes"); }
    }
    final class Carpeta implements Elemento {
        private final String nombre;
        private final List<Elemento> hijos=new ArrayList<>();
        public Carpeta(String nombre){this.nombre=nombre;}
        public void agregar(Elemento e){if(e==this)throw new IllegalArgumentException("Ciclo directo");hijos.add(e);}
        public int bytes(){return hijos.stream().mapToInt(Elemento::bytes).sum();}
        public String nombre(){return nombre;}
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.composite.MainComposite
 * Nota: Elemento.java es un componente; la salida corresponde al cliente MainComposite.java.
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
