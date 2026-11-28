// Tema:
// Un record puede contener otros records. Sus componentes pueden descomponerse con patrones
// anidados.
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java Segmento.java PatronRecord.java
// Ejecutar: java PatronRecord
// Comandos desde la carpeta de la unidad.

record Segmento(Punto inicio, Punto fin) {}

// Salida esperada:
// 3 4
