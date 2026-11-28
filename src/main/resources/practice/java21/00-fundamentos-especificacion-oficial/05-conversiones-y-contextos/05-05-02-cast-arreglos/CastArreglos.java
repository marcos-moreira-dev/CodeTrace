// Tema:
// Un arreglo conserva su tipo real de componente aunque se referencie con un supertipo. Un
// cast a un tipo de arreglo incompatible falla incluso si sus elementos fueran compatibles
// individualmente.
// Compilar: javac --release 21 -encoding UTF-8 -d . ElementoBase.java ElementoColor.java CastArreglos.java
// Ejecutar: java CastArreglos
// Comandos desde la carpeta de la unidad.

public class CastArreglos {
    public static void main(String[] args) {
        ElementoBase[] base = new ElementoColor[2];
        base[0] = new ElementoColor(12);
        ElementoColor[] colores = (ElementoColor[]) base;
        System.out.println(colores[0].color);
        Object objeto = base;
        System.out.println(((ElementoBase[]) objeto).length);
        try { colores = (ElementoColor[]) new ElementoBase[2]; }
        catch (ClassCastException e) { System.out.println("cast inválido"); }
    }
}

// Salida esperada:
// 12
// 2
// cast inválido
