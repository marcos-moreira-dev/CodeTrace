// Tema:
// Después del borrado de tipos, métodos de distintas declaraciones pueden tener la misma firma
// sin constituir una sobrescritura válida. El compilador rechaza ese conflicto.
// Compilar: javac --release 21 -encoding UTF-8 -d . IdentidadGenerica.java IdentidadInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class IdentidadInvalida extends IdentidadGenerica<String> {
    Object copiar(Object x) { return x; }
}

// Salida esperada:
// Error: name clash
