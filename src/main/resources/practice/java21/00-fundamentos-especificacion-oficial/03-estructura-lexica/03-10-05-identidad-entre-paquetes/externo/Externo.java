// Tema:
// Los literales de cadena iguales comparten una referencia aunque se declaren en diferentes
// paquetes. == compara referencias, mientras que intern() recupera la referencia canónica de
// una cadena calculada.
// Compilar: javac --release 21 -encoding UTF-8 -d . saludos/IdentidadCadenas.java saludos/Local.java externo/Externo.java
// Ejecutar: java saludos.IdentidadCadenas
// Comandos desde la carpeta de la unidad.

package externo;
public class Externo {
    public static String PALABRA = "luna";
}

// Salida esperada:
// true
// true
// true
// true
// false
// true
