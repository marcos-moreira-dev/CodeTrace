// Tema:
// El bloque static se ejecuta una vez al inicializar la clase, junto con los
// inicializadores estáticos en orden textual. Construir más instancias no vuelve a
// ejecutarlo.
// Compilar: javac --release 21 -encoding UTF-8 -d . InicializadorEstatico.java
// Ejecutar: java InicializadorEstatico
// Comandos desde la carpeta de la unidad.

public class InicializadorEstatico {
    static int dato = 2;
    static { dato *= 3; System.out.println("static " + dato); }
    public static void main(String[] args) {
        new InicializadorEstatico();
        new InicializadorEstatico();
        System.out.println(dato);
    }
}

// Salida esperada:
// static 6
// 6
