// Tema:
// ? extends Number permite recibir listas de Number o de sus subtipos. Se pueden leer
// elementos como Number, pero no insertar números de un tipo concreto sin conocer el tipo
// exacto de la lista.
// Compilar: javac --release 21 -encoding UTF-8 -d . ComodinAcotado.java
// Ejecutar: java ComodinAcotado
// Comandos desde la carpeta de la unidad.

import java.util.List;
public class ComodinAcotado {
    static double suma(List<? extends Number> lista) {
        double total = 0;
        for (Number n : lista) total += n.doubleValue();
        return total;
    }
    public static void main(String[] args) {
        System.out.println(suma(List.of(2, 3)));
        System.out.println(suma(List.of(1.5, 2.5)));
    }
}

// Salida esperada:
// 5.0
// 4.0
