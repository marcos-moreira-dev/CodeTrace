// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.7
// Compilar: javac --release 21 -encoding UTF-8 EscapesJls.java
// Ejecutar: java EscapesJls
// Las secuencias de escape representan caracteres especiales dentro de literales.

public class EscapesJls {
    public static void main(String[] args) {
        System.out.println("uno\tdos");
        System.out.println("comillas: \"Java\"");
        System.out.println("ruta: C:\\java");
    }
}

// Salida esperada:
// uno	dos
// comillas: "Java"
// ruta: C:\java
