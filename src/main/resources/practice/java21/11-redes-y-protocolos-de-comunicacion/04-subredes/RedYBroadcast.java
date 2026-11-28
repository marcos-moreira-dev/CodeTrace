// Tema:
// Para una subred IPv4 convencional /26, AND con la máscara obtiene la red; OR con el complemento obtiene el broadcast. No se generalizan reglas de hosts a /31 o /32.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 RedYBroadcast.java
// Ejecutar: java RedYBroadcast

public class RedYBroadcast {
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
        int direccion = ip(192, 0, 2, 70), m = mascara(26);
        int red = direccion & m;
        System.out.println("Red: " + texto(red));
        System.out.println("Broadcast: " + texto(red | ~m));
    }
}

// Salida esperada:
// Red: 192.0.2.64
// Broadcast: 192.0.2.127
