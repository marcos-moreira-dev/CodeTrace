// Tema:
// El compilador debe poder demostrar que una variable local se asignó antes de leerse. El
// análisis considera las rutas de control y las salidas del bucle.
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionDefinida.java
// Ejecutar: java AsignacionDefinida
// Comandos desde la carpeta de la unidad.

public class AsignacionDefinida {
    public static void main(String[] args) {
        int valor;
        int n = 2;
        while (true) {
            valor = n;
            if (valor >= 5) break;
            n = 6;
        }
        System.out.println(valor);
    }
}

// Salida esperada:
// 6
