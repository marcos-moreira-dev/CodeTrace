// Tema:
// Los campos de interfaz son implícitamente public static final. Sus inicializadores no pueden
// hacer referencias adelantadas inválidas ni depender directamente de sí mismos.
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
