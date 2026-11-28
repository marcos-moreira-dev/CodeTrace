// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.1.8
// Compilar: javac --release 21 -encoding UTF-8 -d . DesempaquetadoNulo.java
// Ejecutar: java DesempaquetadoNulo
// Comandos desde la carpeta de la unidad.

public class DesempaquetadoNulo {
    public static void main(String[] args) {
        Integer numero = 15;
        int valor = numero;
        System.out.println(valor);

        Integer ausente = null;
        int invalido = ausente; // Provoca NullPointerException.
        System.out.println(invalido);
    }
}

// Salida esperada:
// 15
// Después: NullPointerException (intencional).
