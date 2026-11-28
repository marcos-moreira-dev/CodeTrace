// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.2; ejemplo 8.4.8.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . OrigenMetodo.java DerivadoMetodo.java OcultacionMetodos.java
// Ejecutar: java OcultacionMetodos
// Comandos desde la carpeta de la unidad.

class OrigenMetodo {
    static String clase() { return "origen"; }
    String instancia() { return "origen"; }
}

// Salida esperada:
// origen
// derivado
