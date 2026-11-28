// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.3; ejemplo 8.4.8.3-4
// Compilar: javac --release 21 -encoding UTF-8 -d . IdentidadGenerica.java IdentidadInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class IdentidadInvalida extends IdentidadGenerica<String> {
    Object copiar(Object x) { return x; }
}

// Salida esperada:
// Error: name clash
