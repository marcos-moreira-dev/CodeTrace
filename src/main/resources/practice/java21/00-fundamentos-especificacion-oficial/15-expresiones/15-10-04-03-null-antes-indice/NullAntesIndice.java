// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.10.4; ejemplo 15.10.4-3
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
