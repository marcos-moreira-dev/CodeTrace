// Tema:
// Un prefijo /n fija n bits superiores. Se trata /0 por separado porque Java enmascara la distancia de desplazamiento de int.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 MascaraCIDR.java
// Ejecutar: java MascaraCIDR

public class MascaraCIDR {
    static int mascara(int prefijo) {
        if (prefijo < 0 || prefijo > 32) throw new IllegalArgumentException("Prefijo");
        return prefijo == 0 ? 0 : -1 << (32 - prefijo);
    }
    static int ip(int a, int b, int c, int d) {
        return (a << 24) | (b << 16) | (c << 8) | d;
    }
    static String texto(int x) {
        return (x >>> 24) + "." + ((x >>> 16) & 255) + "." + ((x >>> 8) & 255) + "." + (x & 255);
    }

    public static void main(String[] args) throws Exception {
        for (int n : new int[]{0, 24, 26, 32})
            System.out.println("/" + n + ": " + texto(mascara(n)));
    }
}

// Salida esperada:
// /0: 0.0.0.0
// /24: 255.255.255.0
// /26: 255.255.255.192
// /32: 255.255.255.255
