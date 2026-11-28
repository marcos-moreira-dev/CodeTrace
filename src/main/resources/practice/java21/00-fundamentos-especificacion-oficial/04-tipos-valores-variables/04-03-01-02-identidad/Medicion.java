// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.3.1; ejemplo 4.3.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . IdentidadValores.java Medicion.java
// Ejecutar: java IdentidadValores
// Tema JLS:
// Representa el estado compartido por dos referencias. Solo se crea una instancia;
// modificar su campo mediante una referencia es visible mediante la otra.
// Comandos desde la carpeta de la unidad.

class Medicion {
    int valor;
}

// Salida esperada:
// 7 9
// 18 18
