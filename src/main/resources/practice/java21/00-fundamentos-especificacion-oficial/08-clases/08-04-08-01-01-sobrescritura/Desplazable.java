// Tema:
// Una subclase puede sobrescribir un método de instancia con una implementación propia. La
// llamada mediante una referencia de la superclase selecciona el método de la instancia real.
// Compilar: javac --release 21 -encoding UTF-8 -d . Desplazable.java Limitado.java SobrescrituraMovimiento.java
// Ejecutar: java SobrescrituraMovimiento
// Comandos desde la carpeta de la unidad.

class Desplazable {
    int posicion;
    void mover(int distancia) { posicion += distancia; }
}

// Salida esperada:
// 3
// 0
