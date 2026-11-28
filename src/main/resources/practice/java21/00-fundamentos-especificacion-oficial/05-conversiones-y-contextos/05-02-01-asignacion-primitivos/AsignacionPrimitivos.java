// Tema:
// La asignación admite ampliaciones primitivas implícitas. Las reducciones explícitas a byte
// conservan los ocho bits inferiores y pueden cambiar el valor o el signo.
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
