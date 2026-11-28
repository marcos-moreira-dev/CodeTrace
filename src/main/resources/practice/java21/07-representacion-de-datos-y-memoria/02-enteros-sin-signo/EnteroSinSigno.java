// Tema:
// Los tipos byte e int conservan patrones de bits con signo; la API puede interpretarlos
// sin signo. El ancho de la representación no cambia.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 EnteroSinSigno.java
// Ejecutar: java EnteroSinSigno

public class EnteroSinSigno {
    public static void main(String[] args) {
        byte b = (byte) 0xff;
        int i = -1;
        System.out.println(b);
        System.out.println(Byte.toUnsignedInt(b));
        System.out.println(Integer.toUnsignedLong(i));
    }
}

// Salida esperada:
// -1
// 255
// 4294967295
