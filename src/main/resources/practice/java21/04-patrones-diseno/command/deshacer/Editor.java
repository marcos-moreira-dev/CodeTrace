// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.patrones.command;

/**
 * Receptor del patrón Command: contiene el estado del texto.
 * El comando no posee la UI, sino que encapsula una petición.
 */
public final class Editor {
    private final StringBuilder contenido=new StringBuilder();
    public int longitud(){return contenido.length();}
    public void agregar(String texto){contenido.append(texto);}
    public void borrarDesde(int inicio){contenido.setLength(inicio);}
    public String texto(){return contenido.toString();}
}
