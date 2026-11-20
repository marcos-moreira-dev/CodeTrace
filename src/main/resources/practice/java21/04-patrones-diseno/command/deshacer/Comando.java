// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.patrones.command;

/**
 * Command es un objeto que puede ejecutarse y deshacerse.
 * Se preserva el estado anterior para implementar undo determinista.
 */
public interface Comando {
    void ejecutar();
    void deshacer();
}
final class AgregarTexto implements Comando {
    private final Editor receptor;
    private final String texto;
    private int longitudAnterior=-1;
    AgregarTexto(Editor receptor,String texto){
        this.receptor=receptor;this.texto=texto;
    }
    public void ejecutar(){
        if(longitudAnterior>=0)throw new IllegalStateException("Ya ejecutado");
        longitudAnterior=receptor.longitud();
        receptor.agregar(texto);
    }
    public void deshacer(){
        if(longitudAnterior<0)throw new IllegalStateException("Sin ejecución");
        receptor.borrarDesde(longitudAnterior);
        longitudAnterior=-1;
    }
}
