// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.3; ejemplo 9.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . TarifaA.java TarifaB.java TarifaAmbigua.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface TarifaB { int COSTO = 7; }

// Salida esperada:
// Error: reference to COSTO is ambiguous
