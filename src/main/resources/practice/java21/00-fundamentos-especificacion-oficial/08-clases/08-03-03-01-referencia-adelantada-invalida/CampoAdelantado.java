// Tema:
// Un inicializador de campo de instancia no puede leer por nombre simple otro campo de
// instancia declarado posteriormente en ese contexto.
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoAdelantado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class CampoAdelantado {
    int primero = segundo;
    int segundo = 7;
}

// Salida esperada:
// Error: illegal forward reference
