// Tema:
// Un constructor inicializa la nueva instancia y no declara tipo de retorno. this distingue
// los campos de los parámetros que tienen el mismo nombre.
// Compilar: javac --release 21 -encoding UTF-8 -d . ConstructorSimple.java
// Ejecutar: java ConstructorSimple
// Comandos desde la carpeta de la unidad.

public class ConstructorSimple {
    int ancho, alto;
    ConstructorSimple(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }
    public static void main(String[] args) {
        ConstructorSimple c = new ConstructorSimple(3, 5);
        System.out.println(c.ancho + " " + c.alto);
    }
}

// Salida esperada:
// 3 5
