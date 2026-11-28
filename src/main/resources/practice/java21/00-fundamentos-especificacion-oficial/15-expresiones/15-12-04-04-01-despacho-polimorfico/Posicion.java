// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.4.4; ejemplo 15.12.4.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Posicion.java PosicionColor.java DespachoDinamico.java
// Ejecutar: java DespachoDinamico
// Comandos desde la carpeta de la unidad.

class Posicion {
    int x;
    void mover(int d) { x += d; if (Math.abs(x) >= 10) limpiar(); }
    void limpiar() { System.out.println("posición"); x = 0; }
}

// Salida esperada:
// color
// posición
// 0 0
