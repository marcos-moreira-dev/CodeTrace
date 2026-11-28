// Tema:
// Un campo final de un objeto correctamente construido tiene garantías especiales de
// visibilidad si otro hilo llega a observar la referencia. Otros campos no disfrutan
// automáticamente de esa garantía.
// Compilar: javac --release 21 -encoding UTF-8 -d . DatoPublicado.java PublicacionFinal.java
// Ejecutar: java PublicacionFinal
// Comandos desde la carpeta de la unidad.

class DatoPublicado {
    final int fijo;
    int normal;
    DatoPublicado() { fijo = 7; normal = 9; }
}

// Salida esperada:
// Una de las salidas permitidas, sin garantizar cuál:
// sin referencia
// 7 0
// 7 9
