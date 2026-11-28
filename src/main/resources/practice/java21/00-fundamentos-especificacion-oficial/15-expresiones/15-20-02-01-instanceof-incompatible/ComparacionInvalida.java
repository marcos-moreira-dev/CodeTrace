// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.20.2; ejemplo 15.20.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Estrella.java Mineral.java ComparacionInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class ComparacionInvalida {
    public static void main(String[] args) {
        Mineral m = new Mineral();
        if (m instanceof Estrella) System.out.println((Estrella)m);
    }
}

// Salida esperada:
// Error: incompatible types
