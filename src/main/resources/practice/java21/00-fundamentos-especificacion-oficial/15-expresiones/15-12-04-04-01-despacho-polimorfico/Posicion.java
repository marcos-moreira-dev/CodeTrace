// Tema:
// La invocación de un método de instancia sobrescrito selecciona la implementación de la clase
// real. Una llamada heredada puede terminar invocando un método de la subclase.
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
