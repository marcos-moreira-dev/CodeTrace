// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.29; ejemplo 15.29-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ConstantesCompilacion.java
// Ejecutar: java ConstantesCompilacion
// Comandos desde la carpeta de la unidad.

public class ConstantesCompilacion {
    public static void main(String[] args) {
        final int CONSTANTE = 2 * 3;
        byte b = CONSTANTE;
        String texto = "lu" + "na";
        System.out.println(b);
        System.out.println(texto == "luna");
    }
}

// Salida esperada:
// 6
// true
