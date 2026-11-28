// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" Callbacks
public final class Callbacks {
    private Callbacks() {}

    static void notificar(String texto, Receptor receptor) {
        receptor.recibir(texto);
    }

    static void mostrar(String texto) {
        System.out.println(texto);
    }

    public static void main(String[] args) {
        notificar("Hola", Callbacks::mostrar);

        Runnable tarea = () -> mostrar("Lista");
        tarea.run();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out Callbacks
 * Salida esperada:
 *   Hola
 *   Lista
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
