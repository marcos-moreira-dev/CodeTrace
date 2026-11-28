// Tema:
// Una variable de tipo puede tener varios límites superiores con &. El compilador permite
// utilizar los miembros de todos los límites porque cualquier argumento de tipo debe
// satisfacerlos.
// Compilar: javac --release 21 -encoding UTF-8 -d . MiembrosTipo.java
// Ejecutar: java MiembrosTipo
// Comandos desde la carpeta de la unidad.

public class MiembrosTipo {
    interface Nombrado { String nombre(); }
    static class Base { int codigo() { return 7; } }
    static class Dato extends Base implements Nombrado {
        public String nombre() { return "dato"; }
    }
    static <T extends Base & Nombrado> void mostrar(T valor) {
        System.out.println(valor.codigo());
        System.out.println(valor.nombre());
    }
    public static void main(String[] args) { mostrar(new Dato()); }
}

// Salida esperada:
// 7
// dato
