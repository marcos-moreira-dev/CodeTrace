// Tema:
// Una cláusula catch puede capturar una excepción checked concreta o su supertipo. Si un
// método declara throws IOException, su ejecución puede lanzar FileNotFoundException; ambos
// manejadores en orden de especificidad son válidos.
// Compilar: javac --release 21 -encoding UTF-8 -d . CapturaSubtipo.java
// Ejecutar: java CapturaSubtipo

import java.io.FileNotFoundException;
import java.io.IOException;
public class CapturaSubtipo {
    static void leer() throws IOException {
        throw new FileNotFoundException("archivo");
    }
    public static void main(String[] args) {
        try { throw new FileNotFoundException("primero"); }
        catch (IOException e) { System.out.println("superclase " + e.getMessage()); }
        try { leer(); }
        catch (FileNotFoundException e) { System.out.println("subclase " + e.getMessage()); }
        catch (IOException e) { System.out.println("otra IO"); }
    }
}

// Salida esperada:
// superclase primero
// subclase archivo
