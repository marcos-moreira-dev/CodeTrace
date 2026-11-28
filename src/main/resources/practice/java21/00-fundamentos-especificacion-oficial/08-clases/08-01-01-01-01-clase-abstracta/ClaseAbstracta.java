// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.1.1; ejemplo 8.1.1.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ClaseAbstracta.java Figura.java Cuadrado.java
// Ejecutar: java ClaseAbstracta
// Comandos desde la carpeta de la unidad.

public class ClaseAbstracta {
    public static void main(String[] args) {
        Figura figura = new Cuadrado(4);
        System.out.println(figura.area());
    }
}

// Salida esperada:
// 16
