// Tema:
// Dos campos distintos con el mismo nombre heredados de interfaces diferentes hacen ambiguo el
// nombre simple. El compilador necesita una referencia calificada.
// Compilar: javac --release 21 -encoding UTF-8 -d . TarifaA.java TarifaB.java TarifaAmbigua.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface TarifaA { int COSTO = 4; }

// Salida esperada:
// Error: reference to COSTO is ambiguous
