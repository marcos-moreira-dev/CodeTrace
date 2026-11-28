// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.21; ejemplo 14.21-1
// Compilar: javac --release 21 -encoding UTF-8 -d . YieldSwitch.java
// Ejecutar: java YieldSwitch
// Comandos desde la carpeta de la unidad.

public class YieldSwitch {
    public static void main(String[] args) {
        int opcion = 2;
        int valor = switch (opcion) {
            case 1 -> 10;
            case 2 -> {
                int base = 20;
                yield base + 1;
            }
            default -> 0;
        };
        System.out.println(valor);
    }
}

// Salida esperada:
// 21
