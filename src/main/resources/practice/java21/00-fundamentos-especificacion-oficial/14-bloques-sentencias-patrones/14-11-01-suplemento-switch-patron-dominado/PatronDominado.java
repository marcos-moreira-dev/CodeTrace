// Tema:
// Una etiqueta de patrón que cubre todos los valores de otra etiqueta posterior la domina.
// El compilador rechaza ese orden porque el caso más específico nunca podría seleccionarse.
// Compilar: javac --release 21 -encoding UTF-8 -d . PatronDominado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class PatronDominado {
    static int medir(Object dato) {
        return switch (dato) {
            case CharSequence s -> s.length();
            case String s -> s.length();
            default -> 0;
        };
    }
}

// Salida esperada:
// Error: dominated by a preceding case label
