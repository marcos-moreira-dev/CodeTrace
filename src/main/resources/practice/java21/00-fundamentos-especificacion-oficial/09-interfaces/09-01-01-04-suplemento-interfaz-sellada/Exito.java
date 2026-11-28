// Tema:
// Este record es una de las alternativas autorizadas por Resultado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Resultado.java Exito.java Fallo.java InterfazSellada.java
// Ejecutar: java InterfazSellada
// Comandos desde la carpeta de la unidad.

record Exito(int valor) implements Resultado {}

// Salida esperada:
// valor 4
// error
