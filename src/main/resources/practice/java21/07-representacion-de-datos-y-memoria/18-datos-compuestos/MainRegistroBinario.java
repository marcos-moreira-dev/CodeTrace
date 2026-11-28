// Tema:
// Se transforma un registro en bytes y se reconstruyen sus campos según el mismo formato,
// sin depender de la memoria física del objeto.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 RegistroBinario.java MainRegistroBinario.java
// Ejecutar: java MainRegistroBinario

import java.util.HexFormat;

public class MainRegistroBinario {
    public static void main(String[] args) {
        RegistroBinario registro = new RegistroBinario(42, (short) 7);
        byte[] bytes = registro.codificar();
        System.out.println(HexFormat.of().formatHex(bytes));
        RegistroBinario copia = RegistroBinario.decodificar(bytes);
        System.out.println(copia.id + " " + copia.cantidad);
    }
}

// Salida esperada:
// 0000002a0007
// 42 7
