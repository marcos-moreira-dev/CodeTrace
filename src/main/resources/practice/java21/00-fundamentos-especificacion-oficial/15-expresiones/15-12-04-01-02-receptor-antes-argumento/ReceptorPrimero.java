// Tema:
// El receptor de una llamada se evalúa antes de sus argumentos. Reasignar la variable del
// receptor al calcular un argumento no cambia el receptor ya seleccionado.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReceptorPrimero.java
// Ejecutar: java ReceptorPrimero
// Comandos desde la carpeta de la unidad.

public class ReceptorPrimero {
    public static void main(String[] args) {
        String texto = "sol";
        System.out.println(texto.startsWith(texto = "mar"));
        System.out.println(texto);
    }
}

// Salida esperada:
// false
// mar
