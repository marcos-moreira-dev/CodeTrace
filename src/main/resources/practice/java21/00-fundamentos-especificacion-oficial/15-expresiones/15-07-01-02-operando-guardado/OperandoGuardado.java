// Tema:
// La evaluación de una operación conserva el valor previamente obtenido de su operando
// izquierdo. Una reasignación durante la evaluación derecha no modifica ese valor guardado.
// Compilar: javac --release 21 -encoding UTF-8 -d . OperandoGuardado.java
// Ejecutar: java OperandoGuardado
// Comandos desde la carpeta de la unidad.

public class OperandoGuardado {
    public static void main(String[] args) {
        int a = 8;
        a += (a = 2);
        int b = 8;
        b = b + (b = 2);
        System.out.println(a);
        System.out.println(b);
    }
}

// Salida esperada:
// 10
// 10
