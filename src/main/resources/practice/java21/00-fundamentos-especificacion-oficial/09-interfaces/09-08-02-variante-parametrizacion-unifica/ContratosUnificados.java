// Tema:
// Una interfaz genérica puede declarar dos métodos de firmas distintas que coinciden tras
// una parametrización concreta. Al hacerse equivalentes, los contratos pueden representar
// un solo método funcional.
// Compilar: javac --release 21 -encoding UTF-8 -d . ContratosUnificados.java
// Ejecutar: java ContratosUnificados

public class ContratosUnificados {
    interface Doble<T, N extends Number> {
        void mostrar(T valor);
        void mostrar(N valor);
    }
    @FunctionalInterface
    interface Enteros extends Doble<Integer, Integer> {}
    public static void main(String[] args) {
        Enteros e = n -> System.out.println(n + 1);
        // La vista con comodín elimina la ambigüedad de las dos sobrecargas.
        Doble<Integer, ?> vista = e;
        vista.mostrar(5);
    }
}

// Salida esperada:
// 6
