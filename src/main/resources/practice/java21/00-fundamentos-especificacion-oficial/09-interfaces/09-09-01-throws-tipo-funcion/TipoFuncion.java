// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.9; ejemplo 9.9-1
// Compilar: javac --release 21 -encoding UTF-8 -d . OperacionIO.java OperacionEOF.java OperacionComun.java TipoFuncion.java
// Ejecutar: java TipoFuncion
// Comandos desde la carpeta de la unidad.

public class TipoFuncion {
    public static void main(String[] args) {
        OperacionComun op = () -> { throw new java.io.EOFException("fin"); };
        try { op.hacer(); } catch (java.io.EOFException e) { System.out.println(e.getMessage()); }
    }
}

// Salida esperada:
// fin
