// Tema:
// El operador + se asocia de izquierda a derecha. Una operación con un String concatena; una
// suma entre números anterior a ese punto se realiza numéricamente.
// Compilar: javac --release 21 -encoding UTF-8 -d . Concatenacion.java
// Ejecutar: java Concatenacion
// Comandos desde la carpeta de la unidad.

public class Concatenacion {
    public static void main(String[] args) {
        System.out.println(2 + 3 + "x");
        System.out.println("x" + 2 + 3);
    }
}

// Salida esperada:
// 5x
// x23
