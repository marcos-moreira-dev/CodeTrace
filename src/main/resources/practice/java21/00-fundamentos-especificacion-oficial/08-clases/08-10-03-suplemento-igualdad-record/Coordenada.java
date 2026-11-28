// Tema:
// El record genera equals y hashCode basados en sus componentes, además de toString. No
// hace falta programarlos para este agregado de valores.
// Compilar: javac --release 21 -encoding UTF-8 -d . Coordenada.java IgualdadRecord.java
// Ejecutar: java IgualdadRecord
// Comandos desde la carpeta de la unidad.

record Coordenada(int x, int y) {}

// Salida esperada:
// false
// true
// true
// Coordenada[x=2, y=3]
