// Tema:
// La resolución de una invocación considera los métodos accesibles y aplicables a sus
// argumentos. Un método private de otra clase no puede invocarse directamente.
// Compilar: javac --release 21 -encoding UTF-8 -d . Doble.java Aplicabilidad.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Aplicabilidad extends Doble {
    static long duplicar(long n) { return n * 2; }
    public static void main(String[] args) {
        System.out.println(duplicar(3));
        System.out.println(Doble.duplicar(3)); // privado
    }
}

// Salida esperada:
// Error: has private access
