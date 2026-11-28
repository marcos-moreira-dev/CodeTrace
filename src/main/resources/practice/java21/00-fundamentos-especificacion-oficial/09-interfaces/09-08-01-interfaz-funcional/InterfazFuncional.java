// Tema:
// Una interfaz funcional tiene un único método abstracto efectivo. Una lambda proporciona su
// implementación según el tipo objetivo.
// Compilar: javac --release 21 -encoding UTF-8 -d . InterfazFuncional.java Calculo.java
// Ejecutar: java InterfazFuncional
// Comandos desde la carpeta de la unidad.

public class InterfazFuncional {
    public static void main(String[] args) {
        Calculo doble = n -> n * 2;
        System.out.println(doble.aplicar(5));
    }
}

// Salida esperada:
// 10
