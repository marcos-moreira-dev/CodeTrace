// Tema:
// Las opciones cortas son convenciones de programas de consola. Java entrega argumentos sin interpretarlos; el programa decide sus reglas.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 OpcionesCortas.java
// Ejecutar: java OpcionesCortas -v -n 3

public class OpcionesCortas {
    public static void main(String[] args) throws Exception {
        boolean verbose = false;
        int cantidad = 0;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-v" -> verbose = true;
                case "-n" -> {
                    if (++i == args.length) throw new IllegalArgumentException("Falta cantidad");
                    cantidad = Integer.parseInt(args[i]);
                }
                default -> throw new IllegalArgumentException("Opcion desconocida");
            }
        }
        System.out.println("Detallado: " + verbose);
        System.out.println("Cantidad: " + cantidad);
    }
}

// Salida esperada:
// Detallado: true
// Cantidad: 3
