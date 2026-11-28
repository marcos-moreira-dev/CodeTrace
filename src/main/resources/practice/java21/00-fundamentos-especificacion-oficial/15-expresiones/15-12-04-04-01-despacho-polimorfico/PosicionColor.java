// Tema:
// La invocación de un método de instancia sobrescrito selecciona la implementación de la clase
// real. Una llamada heredada puede terminar invocando un método de la subclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . Posicion.java PosicionColor.java DespachoDinamico.java
// Ejecutar: java DespachoDinamico
// Comandos desde la carpeta de la unidad.

class PosicionColor extends Posicion {
    int color = 5;
    @Override void limpiar() { System.out.println("color"); super.limpiar(); color = 0; }
}

// Salida esperada:
// color
// posición
// 0 0
