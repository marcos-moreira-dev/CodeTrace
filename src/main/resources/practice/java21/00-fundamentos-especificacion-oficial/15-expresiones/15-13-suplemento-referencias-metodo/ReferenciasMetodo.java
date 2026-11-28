// Tema:
// Una referencia de método crea una implementación de una interfaz funcional sin invocar
// todavía el método. Puede nombrar un método estático, un método de instancia ligado a un
// objeto o un método de instancia cuyo receptor se proporcionará después.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciasMetodo.java
// Ejecutar: java ReferenciasMetodo
// Comandos desde la carpeta de la unidad.

public class ReferenciasMetodo {
    public static void main(String[] args) {
        java.util.function.Function<String, Integer> longitud = String::length;
        java.util.function.IntUnaryOperator valor = Math::abs;
        java.util.function.Consumer<String> escribir = System.out::println;
        escribir.accept("longitud " + longitud.apply("Java"));
        System.out.println(valor.applyAsInt(-7));
    }
}

// Salida esperada:
// longitud 4
// 7
