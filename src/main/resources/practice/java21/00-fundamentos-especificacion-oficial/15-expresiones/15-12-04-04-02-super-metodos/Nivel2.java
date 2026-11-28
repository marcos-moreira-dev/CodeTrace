// Tema:
// super permite invocar la implementación de la superclase directa. Un cast a un supertipo no
// elimina el despacho dinámico de una invocación normal.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nivel1.java Nivel2.java SuperMetodos.java
// Ejecutar: java SuperMetodos
// Comandos desde la carpeta de la unidad.

class Nivel2 extends Nivel1 { String texto() { return "2"; } }

// Salida esperada:
// 3
// 2
// 3
// 3
