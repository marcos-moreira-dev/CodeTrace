// Tema:
// El método privado duplicar(int) solo puede usarse desde el contexto de esta clase. El
// método interno lo invoca y devuelve el resultado; la subclase no puede seleccionar
// directamente ese método privado.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseDuplicar.java AplicabilidadValida.java
// Ejecutar: java AplicabilidadValida
// Comandos desde la carpeta de la unidad.

class BaseDuplicar {
    private static int duplicar(int n) { return n * 2; }
    static int interno() { return duplicar(1); }
}

// Salida esperada:
// 2
// 6
