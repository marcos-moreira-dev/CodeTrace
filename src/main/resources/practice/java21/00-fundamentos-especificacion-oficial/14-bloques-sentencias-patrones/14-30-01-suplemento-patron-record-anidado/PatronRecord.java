// Tema:
// El patrón de record extrae componentes y combina patrones anidados. var permite inferir
// los tipos de las variables extraídas.
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java Segmento.java PatronRecord.java
// Ejecutar: java PatronRecord
// Comandos desde la carpeta de la unidad.

public class PatronRecord {
    public static void main(String[] args) {
        Object dato = new Segmento(new Punto(1, 2), new Punto(4, 6));
        if (dato instanceof Segmento(Punto(var x1, var y1), Punto(var x2, var y2))) {
            System.out.println((x2 - x1) + " " + (y2 - y1));
        }
    }
}

// Salida esperada:
// 3 4
