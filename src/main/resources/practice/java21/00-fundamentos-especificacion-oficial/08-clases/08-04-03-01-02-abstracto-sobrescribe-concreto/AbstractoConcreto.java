// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.3.1; ejemplo 8.4.3.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseConcreta.java Obligatoria.java AbstractoConcreto.java
// Ejecutar: java AbstractoConcreto
// Comandos desde la carpeta de la unidad.

public class AbstractoConcreto extends Obligatoria {
    String dato() { return "nuevo"; }
    public static void main(String[] args) { System.out.println(new AbstractoConcreto().dato()); }
}

// Salida esperada:
// nuevo
