// Tema:
// Un método static se oculta y se selecciona por el tipo estático. Un método de instancia
// sobrescrito se selecciona por la clase real del receptor.
// Compilar: javac --release 21 -encoding UTF-8 -d . OrigenMetodo.java DerivadoMetodo.java OcultacionMetodos.java
// Ejecutar: java OcultacionMetodos
// Comandos desde la carpeta de la unidad.

class DerivadoMetodo extends OrigenMetodo {
    static String clase() { return "derivado"; }
    @Override String instancia() { return "derivado"; }
}

// Salida esperada:
// origen
// derivado
