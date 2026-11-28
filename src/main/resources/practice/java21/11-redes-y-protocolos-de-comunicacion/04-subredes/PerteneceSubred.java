// Tema:
// Dos IPv4 pertenecen al mismo prefijo si al aplicar la máscara producen la misma dirección de red.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PerteneceSubred.java
// Ejecutar: java PerteneceSubred

public class PerteneceSubred {
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
        int red = ip(192, 0, 2, 64), m = mascara(26);
        for (int host : new int[]{70, 130}) {
            int direccion = ip(192, 0, 2, host);
            System.out.println(texto(direccion) + ": " + ((direccion & m) == (red & m)));
        }
    }
}

// Salida esperada:
// 192.0.2.70: true
// 192.0.2.130: false
