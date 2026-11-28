// Tema:
// Un miembro sin modificador de acceso está restringido al mismo paquete. Una subclase de otro
// paquete no obtiene acceso por el mero hecho de heredar.
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/Derivado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package otro;
class Derivado extends base.Punto { void prueba() { super.mover(1); } }

// Salida esperada:
// Error: cannot be accessed from outside package
