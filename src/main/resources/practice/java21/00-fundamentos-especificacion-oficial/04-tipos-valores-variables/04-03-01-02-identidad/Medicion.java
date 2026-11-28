// Tema:
// Asignar un primitivo copia su valor; asignar una referencia copia la referencia y no el
// objeto. Dos variables que apuntan a la misma instancia observan las modificaciones de sus
// campos.
// Compilar: javac --release 21 -encoding UTF-8 -d . IdentidadValores.java Medicion.java
// Ejecutar: java IdentidadValores
// Comandos desde la carpeta de la unidad.

class Medicion {
    int valor;
}

// Salida esperada:
// 7 9
// 18 18
