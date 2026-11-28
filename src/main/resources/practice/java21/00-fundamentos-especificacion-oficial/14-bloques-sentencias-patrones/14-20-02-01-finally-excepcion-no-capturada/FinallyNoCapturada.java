// Tema:
// finally se ejecuta también cuando una excepción sale del try sin ser capturada. Si finally
// termina normalmente, la excepción original continúa propagándose.
// Compilar: javac --release 21 -encoding UTF-8 -d . FalloArchivo.java FinallyNoCapturada.java
// Ejecutar: java FinallyNoCapturada
// Comandos desde la carpeta de la unidad.

public class FinallyNoCapturada {
    static void abrir() throws FalloArchivo {
        throw new NullPointerException();
    }
    public static void main(String[] args) {
        try { abrir(); }
        catch (FalloArchivo e) { System.out.println("archivo"); }
        finally { System.out.println("limpieza"); }
    }
}

// Salida esperada:
// limpieza
// Después: NullPointerException (intencional).
