// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.3.1; ejemplo 9.3.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoInterfazInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface CampoInterfazInvalido {
    int A = B;
    int B = 5;
    int C = C + 1;
}

// Salida esperada:
// Error: illegal forward reference
