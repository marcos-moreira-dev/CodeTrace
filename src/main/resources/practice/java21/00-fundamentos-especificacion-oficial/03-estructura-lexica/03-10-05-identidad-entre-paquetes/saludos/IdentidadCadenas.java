// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §3.10.5; ejemplo 3.10.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . saludos/IdentidadCadenas.java saludos/Local.java externo/Externo.java
// Ejecutar: java saludos.IdentidadCadenas
// Comandos desde la carpeta de la unidad.

package saludos;

public class IdentidadCadenas {
    public static void main(String[] args) {
        String palabra = "luna";
        String fragmento = "na";
        String calculada = "lu" + fragmento;

        // Los literales se comparten incluso entre paquetes.
        System.out.println(palabra == "luna");
        System.out.println(palabra == Local.PALABRA);
        System.out.println(palabra == externo.Externo.PALABRA);
        System.out.println(palabra == ("lu" + "na"));
        System.out.println(palabra == calculada);
        System.out.println(palabra == calculada.intern());
    }
}

// Salida esperada:
// true
// true
// true
// true
// false
// true
