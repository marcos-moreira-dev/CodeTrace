// Tema:
// Boxing convierte un primitivo en su envoltorio, por ejemplo int en Integer. Para ciertas
// constantes pequeñas la identidad está garantizada, pero == no debe usarse como comparación
// numérica general de envoltorios.
// Compilar: javac --release 21 -encoding UTF-8 -d . Empaquetado.java
// Ejecutar: java Empaquetado
// Comandos desde la carpeta de la unidad.

public class Empaquetado {
    public static void main(String[] args) {
        Integer a = 42;
        Integer b = 42;
        // Para estas constantes pequeñas, la identidad está garantizada.
        System.out.println(a == b);
        System.out.println(a.intValue());
    }
}

// Salida esperada:
// true
// 42
