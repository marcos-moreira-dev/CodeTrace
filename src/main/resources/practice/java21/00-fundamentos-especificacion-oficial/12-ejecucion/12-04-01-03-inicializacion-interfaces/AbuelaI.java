// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §12.4.1; ejemplo 12.4.1-3
// Compilar: javac --release 21 -encoding UTF-8 -d . AbuelaI.java MadreI.java HijaI.java InicializarInterfaces.java
// Ejecutar: java InicializarInterfaces
// Comandos desde la carpeta de la unidad.

interface AbuelaI { int CONSTANTE = 1; int A = InicializarInterfaces.mostrar("A", 2); }

// Salida esperada:
// 1
// B=3
// C=4
// 3
