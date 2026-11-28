// Tema:
// Un constructor puede utilizarse como referencia de método con ::new. Su firma se
// selecciona a partir de la interfaz funcional destino.
// Compilar: javac --release 21 -encoding UTF-8 -d . Medida.java ReferenciasConstructor.java
// Ejecutar: java ReferenciasConstructor
// Comandos desde la carpeta de la unidad.

record Medida(int valor) {}

// Salida esperada:
// 8
// 3
