// Tema:
// Un conjunto no repite elementos. En un universo finito de cinco valores,
// cada posición indica pertenencia; las operaciones se reducen a lógica.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 OperacionesConjuntos.java
// Ejecutar: java OperacionesConjuntos

public class OperacionesConjuntos {
    public static void main(String[] args) {
        boolean[] a = {true, true, false, true, false};
        boolean[] b = {false, true, true, false, true};
        String union = "", interseccion = "", diferencia = "";
        for (int i = 0; i < a.length; i++) {
            if (a[i] || b[i]) union += i + " ";
            if (a[i] && b[i]) interseccion += i + " ";
            if (a[i] && !b[i]) diferencia += i + " ";
        }
        System.out.println("Union: " + union.trim());
        System.out.println("Interseccion: " + interseccion.trim());
        System.out.println("A menos B: " + diferencia.trim());
    }
}

// Salida esperada:
// Union: 0 1 2 3 4
// Interseccion: 1
// A menos B: 0 3
