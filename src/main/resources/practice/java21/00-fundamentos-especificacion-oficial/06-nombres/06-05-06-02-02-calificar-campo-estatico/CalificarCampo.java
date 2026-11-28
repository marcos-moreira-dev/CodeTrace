// Tema:
// Un campo static puede seleccionarse mediante el nombre de su clase. No hace falta construir
// una instancia para acceder a ese campo compartido.
// Compilar: javac --release 21 -encoding UTF-8 -d . CalificarCampo.java
// Ejecutar: java CalificarCampo
// Comandos desde la carpeta de la unidad.

public class CalificarCampo {
    static int valor = 7;
    public static void main(String[] args) {
        System.out.println(CalificarCampo.valor);
    }
}

// Salida esperada:
// 7
