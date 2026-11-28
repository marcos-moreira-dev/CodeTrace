// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.2; ejemplo 8.9.2-1
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
