// Tema:
// La lambda no posee un tipo funcional autónomo sin contexto. Interfaces funcionales
// distintas pueden utilizar expresiones similares, y sus métodos determinan cómo se
// invocan.
// Compilar: javac --release 21 -encoding UTF-8 -d . TipoObjetivoLambda.java
// Ejecutar: java TipoObjetivoLambda
// Comandos desde la carpeta de la unidad.

public class TipoObjetivoLambda {
    static void mostrar(java.util.function.IntSupplier f) {
        System.out.println(f.getAsInt());
    }
    public static void main(String[] args) {
        java.util.function.Supplier<String> texto = () -> "hola";
        mostrar(() -> 7);
        System.out.println(texto.get());
    }
}

// Salida esperada:
// 7
// hola
