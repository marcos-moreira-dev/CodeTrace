// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §12.4.1; ejemplo 12.4.1-3
// Compilar: javac --release 21 -encoding UTF-8 -d . AbuelaI.java MadreI.java HijaI.java InicializarInterfaces.java
// Ejecutar: java InicializarInterfaces
// Comandos desde la carpeta de la unidad.

public class InicializarInterfaces {
    static int mostrar(String nombre, int n) {
        System.out.println(nombre + "=" + n);
        return n;
    }
    public static void main(String[] args) {
        System.out.println(MadreI.CONSTANTE);
        System.out.println(HijaI.B);
    }
}

// Salida esperada:
// 1
// B=3
// C=4
// 3
