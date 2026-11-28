// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §17.5.3; ejemplo 17.5.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . FinalReflexion.java
// Ejecutar: java FinalReflexion
// Comandos desde la carpeta de la unidad.

class FinalReflexion {
    final int x;
    FinalReflexion() { x = 1; }
    static void cambiar(FinalReflexion a) throws ReflectiveOperationException {
        var campo = FinalReflexion.class.getDeclaredField("x");
        campo.setAccessible(true);
        campo.setInt(a, 2);
    }
    static int diferencia(FinalReflexion a, FinalReflexion b) throws ReflectiveOperationException {
        int antes = a.x;
        cambiar(a);
        int despues = b.x;
        return despues - antes;
    }
    public static void main(String[] args) throws ReflectiveOperationException {
        FinalReflexion objeto = new FinalReflexion();
        System.out.println(diferencia(objeto, objeto));
    }
}

// Salida esperada:
// Posibles resultados: "-1", "0", "1"
