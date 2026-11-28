// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.11.2; ejemplo 15.11.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . NivelI.java Nivel1.java Nivel2.java SuperCampos.java
// Ejecutar: java SuperCampos
// Comandos desde la carpeta de la unidad.

interface NivelI { int x = 0; }

// Salida esperada:
// 3
// 2
// 2
// 1
// 0
