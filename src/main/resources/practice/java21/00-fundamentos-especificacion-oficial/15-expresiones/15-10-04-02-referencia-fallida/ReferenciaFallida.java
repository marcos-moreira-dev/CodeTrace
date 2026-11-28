// Tema:
// Si evaluar la referencia al arreglo lanza una excepción, el índice no llega a evaluarse.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaFallida.java
// Ejecutar: java ReferenciaFallida
// Comandos desde la carpeta de la unidad.

public class ReferenciaFallida {
    static int[] fallo() { throw new IllegalStateException(); }
    public static void main(String[] args) {
        int indice = 1;
        try { fallo()[indice = 2]++; }
        catch (IllegalStateException e) { System.out.println(indice); }
    }
}

// Salida esperada:
// 1
