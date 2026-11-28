// Tema:
// Una clase concreta debe proporcionar una implementación para cada método abstracto exigido
// por las interfaces que implementa, salvo que ya lo herede.
// Compilar: javac --release 21 -encoding UTF-8 -d . Medible.java MedidorIncompleto.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Medible { int medir(); }

// Salida esperada:
// Error: does not override abstract method
