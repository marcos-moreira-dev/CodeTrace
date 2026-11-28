// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.2; ejemplo 5.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionPrimitivos.java
// Ejecutar: java AsignacionPrimitivos
// Comandos desde la carpeta de la unidad.

public class AsignacionPrimitivos {
    public static void main(String[] args) {
        byte b = 20;
        int i = b;
        long l = i;
        float f = l;
        double d = f;
        System.out.println(d);
        byte reducido = (byte) 130;
        System.out.println(reducido);
    }
}

// Salida esperada:
// 20.0
// -126
