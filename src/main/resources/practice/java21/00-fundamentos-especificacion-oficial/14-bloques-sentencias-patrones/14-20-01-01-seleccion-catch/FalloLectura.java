// Tema:
// Cuando se lanza una excepción, se selecciona el primer catch cuyo tipo sea compatible con la
// excepción real. Los demás manejadores no se ejecutan.
// Compilar: javac --release 21 -encoding UTF-8 -d . FalloLectura.java SeleccionCatch.java
// Ejecutar: java SeleccionCatch
// Comandos desde la carpeta de la unidad.

class FalloLectura extends Exception {}

// Salida esperada:
// lectura
