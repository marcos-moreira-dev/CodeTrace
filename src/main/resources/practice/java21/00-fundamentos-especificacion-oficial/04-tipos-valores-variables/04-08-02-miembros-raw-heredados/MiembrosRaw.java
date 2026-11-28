// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.8; ejemplo 4.8-2
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseNumeros.java MiembrosRaw.java
// Ejecutar: java MiembrosRaw
// Comandos desde la carpeta de la unidad.

import java.util.List;
class MiembrosRaw<T> extends BaseNumeros {
    static List<String> etiquetas = List.of("uno");
    List<T> propios() { return List.of(); }
    public static void main(String[] args) {
        MiembrosRaw raw = new MiembrosRaw();
        List<Number> a = raw.numeros(); // Miembro heredado no genérico.
        List<String> b = raw.propios(); // Conversión unchecked.
        List<String> c = MiembrosRaw.etiquetas; // Campo static.
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}

// Salida esperada:
// [3, 4.5]
// []
// [uno]
