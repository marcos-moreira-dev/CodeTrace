// Tema:
// byte admite valores entre -128 y 127. El literal 128 no es representable y su asignación
// implícita debe ser rechazada por el compilador.
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class AsignacionInvalida {
    public static void main(String[] args) {
        byte demasiadoGrande = 128; // Fuera del rango de byte.
        System.out.println(demasiadoGrande);
    }
}

// Salida esperada:
// Error: possible lossy conversion from int to byte
