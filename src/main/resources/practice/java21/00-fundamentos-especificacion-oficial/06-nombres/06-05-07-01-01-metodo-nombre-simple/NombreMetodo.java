// Tema:
// La resolución de un nombre simple de método depende de las declaraciones visibles.
// Calificarlo con la clase exterior permite seleccionar explícitamente otro método.
// Compilar: javac --release 21 -encoding UTF-8 -d . NombreMetodo.java
// Ejecutar: java NombreMetodo
// Comandos desde la carpeta de la unidad.

public class NombreMetodo {
    static void mostrar() { System.out.println("exterior"); }
    static class Interior {
        static void mostrar(int n) { System.out.println(n); }
        void prueba() {
            mostrar(5);
            NombreMetodo.mostrar();
        }
    }
    public static void main(String[] args) { new Interior().prueba(); }
}

// Salida esperada:
// 5
// exterior
