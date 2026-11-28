// Tema:
// Una clase abstracta no se instancia directamente. Puede declarar métodos abstractos que una
// subclase concreta debe implementar para poder crear objetos.
// Compilar: javac --release 21 -encoding UTF-8 -d . ClaseAbstracta.java Figura.java Cuadrado.java
// Ejecutar: java ClaseAbstracta
// Comandos desde la carpeta de la unidad.

class Cuadrado extends Figura {
    int lado;
    Cuadrado(int lado) { this.lado = lado; }
    int area() { return lado * lado; }
}

// Salida esperada:
// 16
