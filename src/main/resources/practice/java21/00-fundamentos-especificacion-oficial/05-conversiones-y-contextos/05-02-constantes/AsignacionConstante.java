// Tema:
// Una constante entera puede asignarse implícitamente a byte si su valor cabe en el rango. No
// es una conversión general de cualquier int.
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionConstante.java
// Ejecutar: java AsignacionConstante
// Comandos desde la carpeta de la unidad.

public class AsignacionConstante {
    public static void main(String[] args) {
        byte limite = 120;
        final int constante = 40;
        byte reducido = constante;
        System.out.println(limite);
        System.out.println(reducido);
    }
}

// Salida esperada:
// 120
// 40
