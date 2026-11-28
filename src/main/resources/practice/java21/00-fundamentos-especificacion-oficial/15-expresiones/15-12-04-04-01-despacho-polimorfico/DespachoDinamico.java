// Tema:
// La invocación de un método de instancia sobrescrito selecciona la implementación de la clase
// real. Una llamada heredada puede terminar invocando un método de la subclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . Posicion.java PosicionColor.java DespachoDinamico.java
// Ejecutar: java DespachoDinamico
// Comandos desde la carpeta de la unidad.

class DespachoDinamico {
    public static void main(String[] args) {
        PosicionColor p = new PosicionColor();
        p.mover(12);
        System.out.println(p.x + " " + p.color);
    }
}

// Salida esperada:
// color
// posición
// 0 0
