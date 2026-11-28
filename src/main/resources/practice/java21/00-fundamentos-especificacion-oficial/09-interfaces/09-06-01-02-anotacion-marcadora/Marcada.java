// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.1; ejemplo 9.6.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Experimental.java Marcada.java
// Ejecutar: java Marcada
// Comandos desde la carpeta de la unidad.

@Experimental
public class Marcada {
    public static void main(String[] args) { System.out.println("experimental"); }
}

// Salida esperada:
// experimental
