// Tema:
// Un inicializador de instancia puede leer un campo static que aparece después en el código.
// La inicialización de clase ocurre antes de construir instancias.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaPosterior.java
// Ejecutar: java ReferenciaPosterior
// Comandos desde la carpeta de la unidad.

public class ReferenciaPosterior {
    double dato = valor;
    static int valor = 9;
    public static void main(String[] args) {
        System.out.println(new ReferenciaPosterior().dato);
        valor = 4;
        System.out.println(new ReferenciaPosterior().dato);
    }
}

// Salida esperada:
// 9.0
// 4.0
