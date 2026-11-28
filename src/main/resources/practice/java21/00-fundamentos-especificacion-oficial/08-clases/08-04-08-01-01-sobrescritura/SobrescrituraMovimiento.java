// Tema:
// Una subclase puede sobrescribir un método de instancia con una implementación propia. La
// llamada mediante una referencia de la superclase selecciona el método de la instancia real.
// Compilar: javac --release 21 -encoding UTF-8 -d . Desplazable.java Limitado.java SobrescrituraMovimiento.java
// Ejecutar: java SobrescrituraMovimiento
// Comandos desde la carpeta de la unidad.

public class SobrescrituraMovimiento {
    public static void main(String[] args) {
        Desplazable d = new Limitado();
        d.mover(10);
        System.out.println(d.posicion);
        d.mover(-8);
        System.out.println(d.posicion);
    }
}

// Salida esperada:
// 3
// 0
