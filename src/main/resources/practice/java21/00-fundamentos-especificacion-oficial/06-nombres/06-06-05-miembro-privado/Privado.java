// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.6; ejemplo 6.6-5
// Compilar: javac --release 21 -encoding UTF-8 -d . Privado.java AccesoPrivado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Privado { private int dato = 5; }

// Salida esperada:
// Error: dato has private access
