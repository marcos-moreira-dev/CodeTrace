// Tema:
// Al combinar métodos abstractos con distintas cláusulas throws, el tipo funcional solo
// permite las excepciones checked compatibles con todos los contratos heredados.
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
