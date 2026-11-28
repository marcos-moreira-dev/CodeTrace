// Tema:
// Una clase miembro static no necesita una instancia de su clase exterior; una clase
// interna no estática sí puede acceder al estado de una instancia exterior.
// Compilar: javac --release 21 -encoding UTF-8 -d . AnidadaEstatica.java
// Ejecutar: java AnidadaEstatica
// Comandos desde la carpeta de la unidad.

public class AnidadaEstatica {
    int dato = 6;
    static class Anidada {
        int leer() { return 3; }
    }
    class Interna {
        int leer() { return dato; }
    }
    public static void main(String[] args) {
        Anidada a = new Anidada();
        Interna b = new AnidadaEstatica().new Interna();
        System.out.println(a.leer() + " " + b.leer());
    }
}

// Salida esperada:
// 3 6
