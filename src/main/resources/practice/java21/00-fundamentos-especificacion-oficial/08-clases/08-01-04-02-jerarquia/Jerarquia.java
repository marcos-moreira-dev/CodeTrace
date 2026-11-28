// Tema:
// Una clase derivada es subtipo de su superclase directa y de los antecesores de esta.
// instanceof comprueba esa relación sobre la instancia real.
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
