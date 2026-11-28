// Tema:
// Una clase abstracta no se instancia directamente. Puede declarar métodos abstractos que una
// subclase concreta debe implementar para poder crear objetos.
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
