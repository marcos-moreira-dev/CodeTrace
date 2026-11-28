// Tema:
// Una opción larga puede expresar un nombre y un valor. Se define una gramática sencilla --nombre=valor, sin interpretar comandos del shell.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 OpcionesLargas.java
// Ejecutar: java OpcionesLargas --archivo=ventas.txt --resumen

public class OpcionesLargas {
    public static void main(String[] args) throws Exception {
        String archivo = "";
        boolean resumen = false;
        for (String arg : args) {
            if (arg.startsWith("--archivo=")) archivo = arg.substring("--archivo=".length());
            else if (arg.equals("--resumen")) resumen = true;
            else throw new IllegalArgumentException("Opcion desconocida");
        }
        System.out.println("Archivo: " + archivo);
        System.out.println("Resumen: " + resumen);
    }
}

// Salida esperada:
// Archivo: ventas.txt
// Resumen: true
