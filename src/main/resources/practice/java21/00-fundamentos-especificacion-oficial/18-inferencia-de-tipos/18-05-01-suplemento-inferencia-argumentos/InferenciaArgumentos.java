// Tema:
// Al invocar un método genérico, el compilador deduce el argumento de tipo a partir de los
// tipos de los argumentos y comprueba sus límites. La variable de tipo conserva la relación
// entre entrada y retorno.
// Compilar: javac --release 21 -encoding UTF-8 -d . InferenciaArgumentos.java
// Ejecutar: java InferenciaArgumentos
// Comandos desde la carpeta de la unidad.

public class InferenciaArgumentos {
    static <T extends Number> T conservar(T numero) {
        return numero;
    }

    public static void main(String[] args) {
        Integer entero = conservar(7);
        Double decimal = conservar(2.5);
        System.out.println(entero);
        System.out.println(decimal);
    }
}

// Salida esperada:
// 7
// 2.5
