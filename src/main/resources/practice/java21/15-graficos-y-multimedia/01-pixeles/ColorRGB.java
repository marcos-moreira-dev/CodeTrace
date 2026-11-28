// Tema:
// Un entero ARGB empaqueta alfa, rojo, verde y azul en cuatro bytes. Las máscaras recuperan cada componente sin extender el signo.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 ColorRGB.java
// Ejecutar: java ColorRGB

public class ColorRGB {
    public static void main(String[] args) throws Exception {
        int argb = (255 << 24) | (12 << 16) | (34 << 8) | 56;
        System.out.println("A: " + (argb >>> 24));
        System.out.println("R: " + ((argb >>> 16) & 255));
        System.out.println("G: " + ((argb >>> 8) & 255));
        System.out.println("B: " + (argb & 255));
    }
}

// Salida esperada:
// A: 255
// R: 12
// G: 34
// B: 56
