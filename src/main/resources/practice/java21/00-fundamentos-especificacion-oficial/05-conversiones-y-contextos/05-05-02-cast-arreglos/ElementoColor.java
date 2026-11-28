// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.5; ejemplo 5.5-2
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
