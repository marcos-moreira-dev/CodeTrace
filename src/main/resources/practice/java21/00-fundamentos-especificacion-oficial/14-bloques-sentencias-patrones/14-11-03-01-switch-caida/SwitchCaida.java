// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.11.3; ejemplo 14.11.3-1
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
