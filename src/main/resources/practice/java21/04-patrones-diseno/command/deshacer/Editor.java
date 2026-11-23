// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Editor de las notas de una ronda en un juego ficticio.
// Patrón command: Deshacer o rehacer acciones encapsuladas.
// Glosario: Ronda: segmento breve de una partida; deshacer: restaurar la nota anterior.
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.command.MainCommand
 * Nota: Editor.java es un componente; la salida corresponde al cliente MainCommand.java.
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
