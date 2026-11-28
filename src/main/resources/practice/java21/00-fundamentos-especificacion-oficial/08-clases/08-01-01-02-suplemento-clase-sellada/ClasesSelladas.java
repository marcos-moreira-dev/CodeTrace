// Tema:
// La jerarquía limita las subclases directas, no necesariamente todos los descendientes. La
// rama non-sealed sigue abierta.
// Compilar: javac --release 21 -encoding UTF-8 -d . FiguraSellada.java CirculoSellado.java FiguraLibre.java ClasesSelladas.java
// Ejecutar: java ClasesSelladas
// Comandos desde la carpeta de la unidad.

public class ClasesSelladas {
    public static void main(String[] args) {
        System.out.println(FiguraSellada.class.isSealed());
        System.out.println(CirculoSellado.class.isSealed());
        System.out.println(FiguraLibre.class.isSealed());
    }
}

// Salida esperada:
// true
// false
// false
