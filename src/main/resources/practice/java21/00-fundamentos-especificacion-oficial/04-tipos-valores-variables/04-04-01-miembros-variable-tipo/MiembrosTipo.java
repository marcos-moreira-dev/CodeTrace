// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.4; ejemplo 4.4-1
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
