// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.5.1; ejemplo 4.5.1-2
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
