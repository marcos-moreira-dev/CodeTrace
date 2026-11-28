// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.20.2; ejemplo 14.20.2-1
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
