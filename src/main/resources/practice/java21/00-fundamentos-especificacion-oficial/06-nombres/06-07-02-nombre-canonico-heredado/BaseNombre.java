// Tema:
// Un tipo miembro heredado puede seleccionarse mediante el nombre de la subclase. Sigue siendo
// el mismo tipo declarado en la superclase y conserva su nombre canónico.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseNombre.java DerivadaNombre.java NombreCanonico.java
// Ejecutar: java NombreCanonico
// Comandos desde la carpeta de la unidad.

class BaseNombre { static class Interna {} }

// Salida esperada:
// true
// BaseNombre.Interna
