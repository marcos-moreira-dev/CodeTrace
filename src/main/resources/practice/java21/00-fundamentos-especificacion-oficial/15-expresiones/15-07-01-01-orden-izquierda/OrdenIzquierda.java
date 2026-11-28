// Tema:
// Java evalúa los operandos de izquierda a derecha. El valor obtenido del operando izquierdo
// se conserva aunque el derecho cambie la variable.
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenIzquierda.java
// Ejecutar: java OrdenIzquierda
// Comandos desde la carpeta de la unidad.

public class OrdenIzquierda {
    public static void main(String[] args) {
        int n = 3;
        int resultado = n + (n = 8);
        System.out.println(resultado);
        System.out.println(n);
    }
}

// Salida esperada:
// 11
// 8
