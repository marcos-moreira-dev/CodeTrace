// Tema:
// Un arreglo conserva su tipo real de componente aunque se referencie con un supertipo. Un
// cast a un tipo de arreglo incompatible falla incluso si sus elementos fueran compatibles
// individualmente.
// Compilar: javac --release 21 -encoding UTF-8 -d . ElementoBase.java ElementoColor.java CastArreglos.java
// Ejecutar: java CastArreglos
// Comandos desde la carpeta de la unidad.

class ElementoColor extends ElementoBase {
    int color;
    ElementoColor(int color) { this.color = color; }
}

// Salida esperada:
// 12
// 2
// cast inválido
