// Tema:
// Una suma de octetos módulo 256 detecta algunos cambios accidentales, pero distintas secuencias pueden tener la misma suma. No es criptografía ni autenticación.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 ChecksumSuma.java
// Ejecutar: java ChecksumSuma

public class ChecksumSuma {
    static int checksum(byte[] datos) {
        int suma = 0;
        for (byte dato : datos) suma = (suma + (dato & 255)) & 255;
        return suma;
    }

    public static void main(String[] args) throws Exception {
        byte[] a = {1, 2, 3};
        byte[] b = {3, 2, 1};
        System.out.println("A: " + checksum(a));
        System.out.println("B: " + checksum(b));
        System.out.println("Colision: " + (checksum(a) == checksum(b)));
    }
}

// Salida esperada:
// A: 6
// B: 6
// Colision: true
