// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §11.3; ejemplo 11.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . LanzarCapturar.java
// Ejecutar: java LanzarCapturar
// Comandos desde la carpeta de la unidad.

public class LanzarCapturar {
    static void calcular() {
        int cero = 0;
        System.out.println(3 / cero);
    }
    public static void main(String[] args) {
        try { calcular(); }
        catch (ArithmeticException e) { System.out.println("capturada"); }
        System.out.println("continúa");
    }
}

// Salida esperada:
// capturada
// continúa
