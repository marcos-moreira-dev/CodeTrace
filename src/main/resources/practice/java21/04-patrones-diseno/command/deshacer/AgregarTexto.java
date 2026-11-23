// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Editor de las notas de una ronda en un juego ficticio.
// Patrón command: Deshacer o rehacer acciones encapsuladas.
// Glosario: Ronda: segmento breve de una partida; deshacer: restaurar la nota anterior.
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.command.MainCommand
 * Nota: AgregarTexto.java es un componente; la salida corresponde al cliente MainCommand.java.
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
