// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.5; ejemplo 5.5-3
// Compilar: javac --release 21 -encoding UTF-8 -d . CastIncompatible.java
// Ejecutar: java CastIncompatible
// Comandos desde la carpeta de la unidad.

public class CastIncompatible {
    public static void main(String[] args) {
        Object dato = Integer.valueOf(4);
        String texto = (String) dato; // El tipo real no es String.
        System.out.println(texto);
    }
}

// Salida esperada:
// (sin salida estándar antes de la excepción)
// Después: ClassCastException (intencional).
