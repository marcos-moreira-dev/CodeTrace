// Tema:
// do-while ejecuta el cuerpo al menos una vez antes de comprobar la condición. Esto permite
// producir un dígito incluso cuando el valor inicial es cero.
// Compilar: javac --release 21 -encoding UTF-8 -d . DoHexadecimal.java
// Ejecutar: java DoHexadecimal
// Comandos desde la carpeta de la unidad.

public class DoHexadecimal {
    static String hexadecimal(int numero) {
        StringBuilder resultado = new StringBuilder();
        do {
            resultado.insert(0, Character.forDigit(numero & 15, 16));
            numero >>>= 4;
        } while (numero != 0);
        return resultado.toString();
    }
    public static void main(String[] args) {
        System.out.println(hexadecimal(0));
        System.out.println(hexadecimal(255));
    }
}

// Salida esperada:
// 0
// ff
