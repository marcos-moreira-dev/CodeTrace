// Tema:
// El resto flotante se calcula a partir de un cociente truncado hacia cero. El signo del resto
// no nulo sigue al dividendo.
// Compilar: javac --release 21 -encoding UTF-8 -d . RestoFlotante.java
// Ejecutar: java RestoFlotante
// Comandos desde la carpeta de la unidad.

public class RestoFlotante {
    public static void main(String[] args) {
        System.out.println(7.5 % 2.0);
        System.out.println(7.5 % -2.0);
        System.out.println(-7.5 % 2.0);
        System.out.println(-7.5 % -2.0);
    }
}

// Salida esperada:
// 1.5
// 1.5
// -1.5
// -1.5
