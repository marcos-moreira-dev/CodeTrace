// Tema:
// Cada constante enum es una instancia del tipo enumerado. Puede recibir argumentos de
// constructor y almacenar campos que describen su valor.
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
