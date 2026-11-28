// Tema:
// Dos enumeraciones representan conjuntos finitos de valores independientes. El producto de
// ambos conjuntos permite crear todas las combinaciones; el orden de declaración determina el
// orden natural del enum.
// Compilar: javac --release 21 -encoding UTF-8 -d . Carta.java Baraja.java
// Ejecutar: java Baraja
// Comandos desde la carpeta de la unidad.

import java.util.ArrayList;
import java.util.List;
class Baraja {
    static List<Carta> nueva() {
        List<Carta> cartas = new ArrayList<>();
        for (Carta.Palo p : Carta.Palo.values())
            for (Carta.Valor v : Carta.Valor.values()) cartas.add(new Carta(v,p));
        return cartas;
    }
    public static void main(String[] args) {
        List<Carta> cartas = nueva();
        System.out.println(cartas.size());
        System.out.println(cartas.getFirst());
        System.out.println(cartas.getLast());
    }
}

// Salida esperada:
// 52
// DOS TREBOL
// AS PICA
