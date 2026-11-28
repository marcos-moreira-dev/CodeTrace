// Tema:
// En el switch tradicional con etiquetas case y dos puntos, la ejecución puede continuar en el
// caso siguiente si no encuentra un break u otra salida abrupta.
// Compilar: javac --release 21 -encoding UTF-8 -d . SwitchCaida.java
// Ejecutar: java SwitchCaida
// Comandos desde la carpeta de la unidad.

public class SwitchCaida {
    public static void main(String[] args) {
        int opcion = 1;
        switch (opcion) {
            case 1: System.out.println("uno");
            case 2: System.out.println("dos"); break;
            default: System.out.println("otro");
        }
    }
}

// Salida esperada:
// uno
// dos
