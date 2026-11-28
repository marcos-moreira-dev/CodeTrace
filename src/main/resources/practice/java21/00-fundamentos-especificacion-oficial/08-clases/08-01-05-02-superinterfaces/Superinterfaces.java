// Tema:
// Una interfaz puede extender otras interfaces. Una clase concreta que la implementa debe
// satisfacer los métodos abstractos heredados de toda la jerarquía.
// Compilar: javac --release 21 -encoding UTF-8 -d . Movible.java Coloreable.java Pintable.java Punto.java PuntoColor.java Superinterfaces.java
// Ejecutar: java Superinterfaces
// Comandos desde la carpeta de la unidad.

class Superinterfaces extends PuntoColor implements Pintable {
    String tipo;
    public void acabado(String s) { tipo = s; }
    public static void main(String[] args) {
        Superinterfaces p = new Superinterfaces();
        p.mover(3); p.color(5); p.acabado("mate");
        System.out.println(p.x + " " + p.tono + " " + p.tipo);
    }
}

// Salida esperada:
// 3 5 mate
