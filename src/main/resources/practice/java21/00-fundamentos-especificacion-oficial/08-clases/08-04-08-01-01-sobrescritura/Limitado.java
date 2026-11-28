// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.1; ejemplo 8.4.8.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Desplazable.java Limitado.java SobrescrituraMovimiento.java
// Ejecutar: java SobrescrituraMovimiento
// Comandos desde la carpeta de la unidad.

class Limitado extends Desplazable {
    @Override
    void mover(int distancia) {
        super.mover(Math.max(-3, Math.min(3, distancia)));
    }
}

// Salida esperada:
// 3
// 0
