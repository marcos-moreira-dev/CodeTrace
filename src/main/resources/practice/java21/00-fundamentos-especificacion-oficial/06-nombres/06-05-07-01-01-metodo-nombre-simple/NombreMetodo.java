// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.7.1; ejemplo 6.5.7.1-1
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
