// Tema:
// La implicación p => q solo es falsa cuando p es verdadera y q es falsa.
// Se representa mediante !p || q.
// Modalidad: modelo matemático.
// Compilar: javac --release 21 -encoding UTF-8 Implicacion.java
// Ejecutar: java Implicacion

public class Implicacion {
    public static void main(String[] args) {
        boolean[] valores = {false, true};
        for (boolean p : valores) {
            for (boolean q : valores) {
                System.out.println(p + " => " + q + ": " + (!p || q));
            }
        }
    }
}

// Salida esperada:
// false => false: true
// false => true: true
// true => false: false
// true => true: true
