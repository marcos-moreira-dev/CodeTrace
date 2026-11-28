// Tema:
// Fuera del paquete, una subclase puede acceder al miembro protected heredado mediante una
// referencia cuyo tipo sea esa subclase o un subtipo suyo, no mediante cualquier referencia a
// la superclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/Punto3D.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package otro;
class Punto3D extends base.Punto {
    void valido(Punto3D p) { p.x++; }
    void invalido(base.Punto p) { p.x++; }
}

// Salida esperada:
// Error: has protected access
