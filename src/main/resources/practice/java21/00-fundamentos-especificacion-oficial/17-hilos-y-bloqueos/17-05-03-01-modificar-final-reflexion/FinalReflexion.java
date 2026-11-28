// Tema:
// Modificar un campo final mediante mecanismos especiales puede invalidar las suposiciones de
// optimización del programa. El resultado observado no debe tratarse como una garantía general
// de mutabilidad.
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
