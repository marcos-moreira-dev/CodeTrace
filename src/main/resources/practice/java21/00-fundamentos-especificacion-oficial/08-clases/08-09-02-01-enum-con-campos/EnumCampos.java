// Tema:
// Cada constante enum es una instancia del tipo enumerado. Puede recibir argumentos de
// constructor y almacenar campos que describen su valor.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nivel.java EnumCampos.java
// Ejecutar: java EnumCampos
// Comandos desde la carpeta de la unidad.

public class EnumCampos {
    public static void main(String[] args) {
        for (Nivel n : Nivel.values()) System.out.println(n + " " + n.valor());
    }
}

// Salida esperada:
// BAJO 1
// MEDIO 5
// ALTO 10
