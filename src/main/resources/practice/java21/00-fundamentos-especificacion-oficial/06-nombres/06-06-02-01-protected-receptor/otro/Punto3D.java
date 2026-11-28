// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.6.2; ejemplo 6.6.2-1
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
