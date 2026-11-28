// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.4; ejemplo 14.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . VarInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class VarInvalida {
    public static void main(String[] args) {
        var dato;
        System.out.println(dato);
    }
}

// Salida esperada:
// Error: cannot infer type for local variable dato
