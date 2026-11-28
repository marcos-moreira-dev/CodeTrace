// Tema:
// El tipo estático de una variable se conoce al compilar, pero la clase de su objeto se conoce
// en ejecución. Una referencia Number puede apuntar a distintos subtipos.
// Compilar: javac --release 21 -encoding UTF-8 -d . TipoYClase.java
// Ejecutar: java TipoYClase
// Comandos desde la carpeta de la unidad.

public class TipoYClase {
    public static void main(String[] args) {
        Number valor = Integer.valueOf(8);
        System.out.println(valor.getClass().getSimpleName());
        valor = Double.valueOf(2.5);
        System.out.println(valor.getClass().getSimpleName());
    }
}

// Salida esperada:
// Integer
// Double
