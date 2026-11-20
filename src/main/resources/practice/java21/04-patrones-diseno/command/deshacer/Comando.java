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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.command.MainCommand
 * Nota: Comando.java es un componente; la salida corresponde al cliente MainCommand.java.
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
