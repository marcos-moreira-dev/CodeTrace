// Tema:
// Esta alternativa contiene el mensaje de error. Ambas alternativas son tipos concretos de
// Resultado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Resultado.java Exito.java Fallo.java InterfazSellada.java
// Ejecutar: java InterfazSellada
// Comandos desde la carpeta de la unidad.

record Fallo(String mensaje) implements Resultado {}

// Salida esperada:
// valor 4
// error
