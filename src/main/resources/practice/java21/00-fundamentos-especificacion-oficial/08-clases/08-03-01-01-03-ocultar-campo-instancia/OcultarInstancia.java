// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.1.1; ejemplo 8.3.1.1-3
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseInstancia.java OcultarInstancia.java
// Ejecutar: java OcultarInstancia
// Comandos desde la carpeta de la unidad.

public class OcultarInstancia extends BaseInstancia {
    double dato = 6.5;
    void mostrar() { System.out.println(dato + " " + super.dato); }
    public static void main(String[] args) {
        OcultarInstancia x = new OcultarInstancia();
        x.mostrar();
        System.out.println(x.dato + " " + ((BaseInstancia) x).dato);
    }
}

// Salida esperada:
// 6.5 3
// 6.5 3
