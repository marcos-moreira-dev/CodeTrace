// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.2
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
