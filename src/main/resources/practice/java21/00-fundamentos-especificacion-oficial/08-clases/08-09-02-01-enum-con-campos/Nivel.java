// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.2; ejemplo 8.9.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Nivel.java EnumCampos.java
// Ejecutar: java EnumCampos
// Comandos desde la carpeta de la unidad.

enum Nivel {
    BAJO(1), MEDIO(5), ALTO(10);
    private final int valor;
    Nivel(int valor) { this.valor = valor; }
    int valor() { return valor; }
}

// Salida esperada:
// BAJO 1
// MEDIO 5
// ALTO 10
