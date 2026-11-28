// Tema:
// Un método static se oculta y se selecciona por el tipo estático. Un método de instancia
// sobrescrito se selecciona por la clase real del receptor.
// Compilar: javac --release 21 -encoding UTF-8 -d . OrigenMetodo.java DerivadoMetodo.java OcultacionMetodos.java
// Ejecutar: java OcultacionMetodos
// Comandos desde la carpeta de la unidad.

public class OcultacionMetodos {
    public static void main(String[] args) {
        OrigenMetodo ref = new DerivadoMetodo();
        System.out.println(ref.clase());
        System.out.println(ref.instancia());
    }
}

// Salida esperada:
// origen
// derivado
