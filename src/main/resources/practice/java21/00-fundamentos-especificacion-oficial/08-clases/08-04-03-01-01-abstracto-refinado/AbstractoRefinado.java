// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.3.1; ejemplo 8.4.3.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseAbstracta.java Refinada.java AbstractoRefinado.java
// Ejecutar: java AbstractoRefinado
// Comandos desde la carpeta de la unidad.

public class AbstractoRefinado extends Refinada {
    Integer obtener() { return 5; }
    public static void main(String[] args) { System.out.println(new AbstractoRefinado().obtener()); }
}

// Salida esperada:
// 5
