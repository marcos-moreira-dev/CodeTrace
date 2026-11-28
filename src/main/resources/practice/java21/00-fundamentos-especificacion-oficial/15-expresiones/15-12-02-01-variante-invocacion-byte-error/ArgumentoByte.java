// Tema:
// Una constante int representable en byte puede asignarse a una variable byte, pero la
// conversión de invocación no permite esa reducción implícita. Se necesita un argumento
// byte o un cast.
// Compilar: javac --release 21 -encoding UTF-8 -d . ArgumentoByte.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class ArgumentoByte {
    static void color(byte valor) {}
    public static void main(String[] args) {
        byte b = 37;
        color(b);
        color(37);
    }
}

// Salida esperada:
// Error: possible lossy conversion from int to byte
