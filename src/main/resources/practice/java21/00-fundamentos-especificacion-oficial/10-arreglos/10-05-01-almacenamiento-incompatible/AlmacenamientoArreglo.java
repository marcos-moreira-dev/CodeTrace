// Tema:
// Aunque un arreglo de Integer pueda referenciarse como Number[], solo admite componentes
// compatibles con Integer. La comprobación en ejecución evita una escritura inválida.
// Compilar: javac --release 21 -encoding UTF-8 -d . AlmacenamientoArreglo.java
// Ejecutar: java AlmacenamientoArreglo
// Comandos desde la carpeta de la unidad.

public class AlmacenamientoArreglo {
    public static void main(String[] args) {
        Number[] numeros = new Integer[2];
        numeros[0] = 4;
        System.out.println(numeros[0]);
        numeros[1] = 2.5;
    }
}

// Salida esperada:
// 4
// Después: ArrayStoreException (intencional).
