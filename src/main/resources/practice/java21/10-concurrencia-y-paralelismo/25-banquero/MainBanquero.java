// Tema:
// Algoritmo del banquero: simular una secuencia de finalización con recursos disponibles, asignación y necesidad restante. Si todos pueden terminar, el estado es seguro; inseguro no significa que ya exista deadlock.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Banquero.java MainBanquero.java
// Ejecutar: java MainBanquero

public class MainBanquero {
    public static void main(String[] args) throws Exception {
        int[][] asignacion = {{1, 0}, {1, 1}, {1, 0}};
        int[][] necesidad = {{1, 1}, {2, 1}, {3, 2}};
        System.out.println("Seguro: " + Banquero.seguro(new int[]{1, 1}, asignacion, necesidad));
        System.out.println("Seguro: " + Banquero.seguro(new int[]{0, 0}, asignacion, necesidad));
    }
}

// Salida esperada:
// Seguro: true
// Seguro: false
