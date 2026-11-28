// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.7.1; ejemplo 15.7.1-3
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
