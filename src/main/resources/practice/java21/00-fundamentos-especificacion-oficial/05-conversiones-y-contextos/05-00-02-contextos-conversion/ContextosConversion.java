// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.0; ejemplo 5.0-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ContextosConversion.java
// Ejecutar: java ContextosConversion
// Comandos desde la carpeta de la unidad.

public class ContextosConversion {
    public static void main(String[] args) {
        int entero = (int) 7.9;
        float flotante = entero;
        System.out.println(entero);
        System.out.println(flotante);
        System.out.println(flotante * entero);
        System.out.println(Math.sqrt(flotante * entero));
    }
}

// Salida esperada:
// 7
// 7.0
// 49.0
// 7.0
