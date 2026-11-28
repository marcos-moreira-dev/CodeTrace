// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.1; ejemplo 8.4.8.1-1
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
