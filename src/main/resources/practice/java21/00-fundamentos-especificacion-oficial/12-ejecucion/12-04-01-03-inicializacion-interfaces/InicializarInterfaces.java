// Tema:
// Leer una constante de compilación no requiere inicializar su interfaz. Leer otro campo
// static inicializa la interfaz que lo declara, no necesariamente todas sus superinterfaces.
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
