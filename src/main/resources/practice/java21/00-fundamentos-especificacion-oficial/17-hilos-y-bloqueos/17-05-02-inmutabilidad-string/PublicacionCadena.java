// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §17.5; ejemplo 17.5-2
// Compilar: javac --release 21 -encoding UTF-8 -d . PublicacionCadena.java
// Ejecutar: java PublicacionCadena
// Comandos desde la carpeta de la unidad.

class PublicacionCadena {
    static String compartida;
    public static void main(String[] args) throws InterruptedException {
        Thread escritor = new Thread(() -> compartida = "prefijo:luz".substring(8));
        Thread lector = new Thread(() -> {
            String vista = compartida;
            if (vista == null) System.out.println("sin referencia");
            else System.out.println(vista);
        });
        escritor.start(); lector.start();
        escritor.join(); lector.join();
        // Una cadena observada conserva su valor: no se convierte en otra.
    }
}

// Salida esperada:
// Posibles resultados: "sin referencia", "luz"
