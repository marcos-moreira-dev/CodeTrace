// Tema:
// Una clase anónima puede invocar un método del objeto exterior por nombre simple si no
// posee ningún miembro con ese nombre. Si existe un método con ese nombre en la clase
// interior, se busca allí; no se vuelve al exterior solo porque los parámetros no
// coincidan.
// Compilar: javac --release 21 -encoding UTF-8 -d . BusquedaExterior.java
// Ejecutar: java BusquedaExterior

public class BusquedaExterior {
    void mostrar(int n) { System.out.println(n); }
    static class Base {
        void guardar(String s) { System.out.println(s); }
    }
    void probar() {
        new Base() {
            {
                mostrar(3);
                guardar("interior");
            }
        };
    }
    public static void main(String[] args) { new BusquedaExterior().probar(); }
}

// Salida esperada:
// 3
// interior
