// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.3; ejemplo 8.9.3-4
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
