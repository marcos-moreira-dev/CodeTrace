// Tema:
// Cada fila representa una combinación de valores de verdad.
// AND exige ambos verdaderos; OR exige al menos uno; XOR exige exactamente uno.
// Modalidad: modelo matemático.
// Compilar: javac --release 21 -encoding UTF-8 TablaVerdad.java
// Ejecutar: java TablaVerdad

public class TablaVerdad {
    public static void main(String[] args) {
        boolean[] valores = {false, true};
        for (boolean p : valores) {
            for (boolean q : valores) {
                System.out.println(p + " " + q + " -> " + (p && q)
                        + " " + (p || q) + " " + (p ^ q));
            }
        }
    }
}

// Salida esperada:
// false false -> false false false
// false true -> false true true
// true false -> false true true
// true true -> true true false
