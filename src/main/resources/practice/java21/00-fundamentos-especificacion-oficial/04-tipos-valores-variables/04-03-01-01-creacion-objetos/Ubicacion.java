// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.3.1; ejemplo 4.3.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . CreacionObjetos.java Ubicacion.java
// Ejecutar: java CreacionObjetos
// Comandos desde la carpeta de la unidad.

class Ubicacion {
    static Ubicacion ORIGEN = new Ubicacion(0);
    int x;
    Ubicacion() { x = 1; }
    Ubicacion(int x) { this.x = x; }
    public String toString() { return "[" + x + "]"; }
}

// Salida esperada:
// [0]
// [1]
// [2] [4]
// ab
