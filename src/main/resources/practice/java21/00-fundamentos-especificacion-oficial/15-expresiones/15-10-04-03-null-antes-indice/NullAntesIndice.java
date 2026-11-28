// Tema:
// En un acceso a arreglo, la expresión de índice se evalúa antes de comprobar si la referencia
// obtenida es null. Por ello sus efectos pueden ocurrir antes de NullPointerException.
// Compilar: javac --release 21 -encoding UTF-8 -d . NullAntesIndice.java
// Ejecutar: java NullAntesIndice
// Comandos desde la carpeta de la unidad.

public class NullAntesIndice {
    public static void main(String[] args) {
        int[] a = null;
        int indice = 1;
        try { a[indice = 2]++; }
        catch (NullPointerException e) { System.out.println(indice); }
    }
}

// Salida esperada:
// 2
