// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

package codetrace.practica.patrones.command;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.command.MainCommand; salida esperada: Hola. */

/** Comando concreto que guarda el estado anterior para deshacer. */
public final class AgregarTexto implements Comando {
    private final Editor receptor;
    private final String texto;
    private int longitudAnterior=-1;
    public AgregarTexto(Editor receptor,String texto){
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
