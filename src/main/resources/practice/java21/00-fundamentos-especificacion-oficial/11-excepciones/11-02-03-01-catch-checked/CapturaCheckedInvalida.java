// Tema:
// Un catch para una excepción checked específica debe corresponder a una excepción que el
// bloque try pueda lanzar según el análisis del compilador.
// Compilar: javac --release 21 -encoding UTF-8 -d . CapturaCheckedInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.io.IOException;
public class CapturaCheckedInvalida {
    public static void main(String[] args) {
        try { System.out.println("sin IO"); }
        catch (IOException e) { System.out.println(e); }
    }
}

// Salida esperada:
// Error: exception IOException is never thrown
