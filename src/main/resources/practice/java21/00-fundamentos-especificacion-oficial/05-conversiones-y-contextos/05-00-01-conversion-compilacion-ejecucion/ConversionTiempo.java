// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.0; ejemplo 5.0-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ConversionTiempo.java
// Ejecutar: java ConversionTiempo
// Comandos desde la carpeta de la unidad.

public class ConversionTiempo {
    public static void main(String[] args) {
        Thread hilo = new Thread();
        Object objeto = hilo;
        Thread recuperado = (Thread) objeto;
        System.out.println(hilo == recuperado);
        int entero = -12;
        long ampliado = entero;
        System.out.println(ampliado);
        double fraccion = 7.9;
        long reducido = (long) fraccion;
        System.out.println(reducido);
        try { Thread invalido = (Thread) (Object) "texto"; }
        catch (ClassCastException e) { System.out.println("cast inválido"); }
    }
}

// Salida esperada:
// true
// -12
// 7
// cast inválido
