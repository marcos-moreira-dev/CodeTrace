// Tema:
// Un tipo miembro heredado puede seleccionarse mediante el nombre de la subclase. Sigue siendo
// el mismo tipo declarado en la superclase y conserva su nombre canónico.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseNombre.java DerivadaNombre.java NombreCanonico.java
// Ejecutar: java NombreCanonico
// Comandos desde la carpeta de la unidad.

public class NombreCanonico {
    public static void main(String[] args) {
        System.out.println(DerivadaNombre.Interna.class == BaseNombre.Interna.class);
        System.out.println(DerivadaNombre.Interna.class.getCanonicalName());
    }
}

// Salida esperada:
// true
// BaseNombre.Interna
