// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.3; ejemplo 8.3.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoAdelantado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class CampoAdelantado {
    int primero = segundo;
    int segundo = 7;
}

// Salida esperada:
// Error: illegal forward reference
