// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.9; ejemplo 8.8.9-2
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Exterior.java otro/Derivada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package otro;
public class Derivada extends base.Exterior {
    void crear() { new Interna(); }
}

// Salida esperada:
// Error: has protected access
