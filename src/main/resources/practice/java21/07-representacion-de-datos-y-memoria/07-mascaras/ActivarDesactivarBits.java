// Tema:
// Una máscara con un solo bit permite activar con OR, desactivar con AND y complemento, y
// alternar con XOR.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 ActivarDesactivarBits.java
// Ejecutar: java ActivarDesactivarBits

public class ActivarDesactivarBits {
    public static void main(String[] args) {
        int estado = 0;
        int bit = 1 << 3;
        estado |= bit;
        System.out.println(estado);
        estado &= ~bit;
        System.out.println(estado);
        estado ^= bit;
        System.out.println(estado);
    }
}

// Salida esperada:
// 8
// 0
// 8
