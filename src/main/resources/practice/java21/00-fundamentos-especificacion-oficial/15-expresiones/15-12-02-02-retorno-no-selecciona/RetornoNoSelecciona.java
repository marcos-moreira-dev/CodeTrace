// Tema:
// La sobrecarga se resuelve según los parámetros aplicables, no según el tipo de retorno que
// el cliente desea recibir. Una asignación incompatible puede fallar después de seleccionar el
// método.
// Compilar: javac --release 21 -encoding UTF-8 -d . Figura.java Circulo.java RetornoNoSelecciona.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class RetornoNoSelecciona {
    static int describir(Circulo c) { return 3; }
    static String describir(Figura f) { return "figura"; }
    public static void main(String[] args) {
        String texto = describir(new Circulo());
    }
}

// Salida esperada:
// Error: int cannot be converted to String
