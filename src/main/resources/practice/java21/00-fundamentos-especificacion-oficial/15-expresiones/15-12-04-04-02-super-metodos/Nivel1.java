// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.4.4; ejemplo 15.12.4.4-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Nivel1.java Nivel2.java SuperMetodos.java
// Ejecutar: java SuperMetodos
// Comandos desde la carpeta de la unidad.

class Nivel1 { String texto() { return "1"; } }

// Salida esperada:
// 3
// 2
// 3
// 3
