// Tema:
// Un bloque inicializador de instancia se ejecuta al construir cada objeto, después del
// constructor de superclase y en el orden textual de inicializadores de instancia, antes
// del cuerpo del constructor.
// Compilar: javac --release 21 -encoding UTF-8 -d . InicializadorInstancia.java
// Ejecutar: java InicializadorInstancia
// Comandos desde la carpeta de la unidad.

public class InicializadorInstancia {
    int dato = 2;
    { dato *= 3; System.out.println("bloque " + dato); }
    InicializadorInstancia() { System.out.println("constructor " + dato); }
    public static void main(String[] args) {
        new InicializadorInstancia();
        new InicializadorInstancia();
    }
}

// Salida esperada:
// bloque 6
// constructor 6
// bloque 6
// constructor 6
