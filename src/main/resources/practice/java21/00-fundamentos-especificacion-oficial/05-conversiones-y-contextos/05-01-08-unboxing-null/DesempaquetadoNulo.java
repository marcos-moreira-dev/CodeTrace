// Tema:
// Unboxing extrae el primitivo de un envoltorio. Si la referencia es null no existe un objeto
// del que extraer el valor y se lanza NullPointerException.
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
