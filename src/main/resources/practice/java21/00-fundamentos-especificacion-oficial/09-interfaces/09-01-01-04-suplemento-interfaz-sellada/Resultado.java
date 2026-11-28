// Tema:
// Una interfaz sealed controla las clases o subinterfaces que la implementan directamente.
// Los records son implícitamente final.
// Compilar: javac --release 21 -encoding UTF-8 -d . Resultado.java Exito.java Fallo.java InterfazSellada.java
// Ejecutar: java InterfazSellada
// Comandos desde la carpeta de la unidad.

sealed interface Resultado permits Exito, Fallo {}

// Salida esperada:
// valor 4
// error
