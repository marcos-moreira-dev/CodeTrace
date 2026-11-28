// Tema:
// La resolución de sobrecargas considera los métodos accesibles y aplicables. El método
// privado de la superclase no participa en la llamada de la subclase, por lo que el
// argumento int se amplía a long.
// Compilar: javac --release 21 -encoding UTF-8 -d . AplicabilidadValida.java
// Ejecutar: java AplicabilidadValida

class BaseDuplicar {
    private static int duplicar(int n) { return n * 2; }
    static int interno() { return duplicar(1); }
}
public class AplicabilidadValida extends BaseDuplicar {
    static long duplicar(long n) { return n * 2; }
    public static void main(String[] args) {
        System.out.println(interno());
        System.out.println(duplicar(3));
    }
}

// Salida esperada:
// 2
// 6
