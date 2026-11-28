// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.3; ejemplo 8.9.3-4
// Compilar: javac --release 21 -encoding UTF-8 -d . Carta.java Baraja.java
// Ejecutar: java Baraja
// Comandos desde la carpeta de la unidad.

class Carta implements Comparable<Carta> {
    enum Valor { DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, JOTA, REINA, REY, AS }
    enum Palo { TREBOL, DIAMANTE, CORAZON, PICA }
    final Valor valor;
    final Palo palo;
    Carta(Valor valor, Palo palo) {
        this.valor = java.util.Objects.requireNonNull(valor);
        this.palo = java.util.Objects.requireNonNull(palo);
    }
    public int compareTo(Carta otra) {
        int comparacion = palo.compareTo(otra.palo);
        return comparacion != 0 ? comparacion : valor.compareTo(otra.valor);
    }
    public String toString() { return valor + " " + palo; }
}

// Salida esperada:
// 52
// DOS TREBOL
// AS PICA
