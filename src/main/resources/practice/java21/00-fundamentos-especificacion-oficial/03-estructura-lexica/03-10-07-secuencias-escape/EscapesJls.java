// Tema:
// La barra invertida introduce caracteres especiales como tabulaciones, comillas o barras
// literales. El texto escrito en el archivo fuente no siempre coincide con los caracteres
// almacenados.
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
