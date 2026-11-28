// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.6.1; ejemplo 6.5.6.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . InstanciaInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class InstanciaInvalida {
    static String prefijo = "a";
    String sufijo = "b";
    String unir() { return prefijo + sufijo; }
    static String unirEstatico() { return prefijo + sufijo; }
}

// Salida esperada:
// Error: non-static variable sufijo cannot be referenced from a static context
