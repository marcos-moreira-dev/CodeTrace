// Tema:
// Si la evaluación del operando izquierdo termina abruptamente con una excepción, el operando
// derecho no se evalúa.
// Compilar: javac --release 21 -encoding UTF-8 -d . OperandoInterrumpido.java
// Ejecutar: java OperandoInterrumpido
// Comandos desde la carpeta de la unidad.

public class OperandoInterrumpido {
    static int fallo() { throw new IllegalStateException("falló"); }
    public static void main(String[] args) {
        int dato = 4;
        try { int resultado = fallo() / (dato = 9); }
        catch (IllegalStateException e) { System.out.println(dato); }
    }
}

// Salida esperada:
// 4
