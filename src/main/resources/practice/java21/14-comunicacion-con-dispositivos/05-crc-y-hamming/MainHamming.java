// Tema:
// Hamming(7,4): posiciones 1,2,4 de paridad y 3,5,6,7 de datos. El síndrome identifica un bit erróneo bajo el supuesto de un solo error; no garantiza corrección de errores múltiples.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 Hamming74.java MainHamming.java
// Ejecutar: java MainHamming

public class MainHamming {
    public static void main(String[] args) {
        int[] palabra = Hamming74.codificar(11);
        System.out.println("Sindrome original: " + Hamming74.sindrome(palabra));
        palabra[5] ^= 1;
        System.out.println("Posicion alterada: " + Hamming74.sindrome(palabra));
        System.out.println("Dato recuperado: " + Hamming74.recuperar(palabra));
    }
}

// Salida esperada:
// Sindrome original: 0
// Posicion alterada: 5
// Dato recuperado: 11
