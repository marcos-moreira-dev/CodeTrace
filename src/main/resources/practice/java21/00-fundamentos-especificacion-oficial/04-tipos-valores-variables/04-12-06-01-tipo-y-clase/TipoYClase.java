// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.6; ejemplo 4.12.6-1
// Compilar: javac --release 21 -encoding UTF-8 -d . TipoYClase.java
// Ejecutar: java TipoYClase
// Comandos desde la carpeta de la unidad.

public class TipoYClase {
    public static void main(String[] args) {
        Number valor = Integer.valueOf(8);
        System.out.println(valor.getClass().getSimpleName());
        valor = Double.valueOf(2.5);
        System.out.println(valor.getClass().getSimpleName());
    }
}

// Salida esperada:
// Integer
// Double
