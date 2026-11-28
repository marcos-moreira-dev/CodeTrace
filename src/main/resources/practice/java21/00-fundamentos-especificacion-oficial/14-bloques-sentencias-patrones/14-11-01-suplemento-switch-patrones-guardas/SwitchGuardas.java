// Tema:
// Un case con patrón puede incluir una guarda when que comprueba una condición adicional.
// Se prueban las etiquetas en orden y case null permite tratar explícitamente una
// referencia nula.
// Compilar: javac --release 21 -encoding UTF-8 -d . SwitchGuardas.java
// Ejecutar: java SwitchGuardas
// Comandos desde la carpeta de la unidad.

public class SwitchGuardas {
    static String describir(Object dato) {
        return switch (dato) {
            case null -> "nulo";
            case String s when s.isEmpty() -> "vacío";
            case String s -> "texto " + s;
            case Integer n -> "entero " + n;
            default -> "otro";
        };
    }
    public static void main(String[] args) {
        System.out.println(describir(null));
        System.out.println(describir(""));
        System.out.println(describir("Java"));
        System.out.println(describir(5));
    }
}

// Salida esperada:
// nulo
// vacío
// texto Java
// entero 5
