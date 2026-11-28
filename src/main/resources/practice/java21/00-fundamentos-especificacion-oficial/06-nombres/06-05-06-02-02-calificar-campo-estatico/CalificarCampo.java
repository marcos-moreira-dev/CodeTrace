// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.6.2; ejemplo 6.5.6.2-2
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
