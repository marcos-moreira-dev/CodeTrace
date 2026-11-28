// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.5; ejemplo 8.1.5-4
// Compilar: javac --release 21 -encoding UTF-8 -d . Medible.java MedidorIncompleto.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class MedidorIncompleto implements Medible {}

// Salida esperada:
// Error: does not override abstract method
