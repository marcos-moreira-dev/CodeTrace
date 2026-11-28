// Tema:
// super selecciona el campo visible de la superclase. Un cast de la referencia permite
// seleccionar un campo oculto según el tipo estático resultante.
// Compilar: javac --release 21 -encoding UTF-8 -d . NivelI.java Nivel1.java Nivel2.java SuperCampos.java
// Ejecutar: java SuperCampos
// Comandos desde la carpeta de la unidad.

class Nivel1 implements NivelI { int x = 1; }

// Salida esperada:
// 3
// 2
// 2
// 1
// 0
