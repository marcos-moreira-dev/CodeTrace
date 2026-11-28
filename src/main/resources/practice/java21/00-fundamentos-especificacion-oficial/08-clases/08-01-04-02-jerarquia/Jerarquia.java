// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.4; ejemplo 8.1.4-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Antepasado.java Intermedio.java Descendiente.java Jerarquia.java
// Ejecutar: java Jerarquia
// Comandos desde la carpeta de la unidad.

public class Jerarquia {
    public static void main(String[] args) {
        Descendiente d = new Descendiente();
        System.out.println(d instanceof Intermedio);
        System.out.println(d instanceof Antepasado);
        System.out.println(Descendiente.class.getSuperclass() == Intermedio.class);
    }
}

// Salida esperada:
// true
// true
// true
