// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.4
// Compilar: javac --release 21 -encoding UTF-8 LiteralesCaracterJls.java
// Ejecutar: java LiteralesCaracterJls
// char representa una unidad UTF-16; se escribe entre comillas simples.

public class LiteralesCaracterJls {
    public static void main(String[] args) {
        char letra = 'A';
        char simbolo = '\u03A9';
        System.out.println(letra);
        System.out.println(simbolo);
    }
}

// Salida esperada:
// A
// Ω
