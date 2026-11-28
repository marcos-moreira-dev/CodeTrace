// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §11.2.3; ejemplo 11.2.3-1
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
