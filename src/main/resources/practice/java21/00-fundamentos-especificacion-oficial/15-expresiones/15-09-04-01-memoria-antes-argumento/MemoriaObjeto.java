// Tema:
// La creación de una instancia reserva memoria antes de evaluar los argumentos del
// constructor. Si no hay memoria suficiente, esos argumentos pueden no llegar a evaluarse.
// Ejecutar solo con el límite de heap indicado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nodo.java MemoriaObjeto.java
// Ejecutar: java -Xmx16m -Xint MemoriaObjeto
// Comandos desde la carpeta de la unidad.

class MemoriaObjeto {
    public static void main(String[] args) {
        // Ejecutar solo con el límite de heap indicado. La lista retiene los objetos.
        new Nodo(0);
        System.out.println("heap limitado");
        int intento = 0, argumento = 0;
        try {
            for (;;) {
                intento++;
                new Nodo(argumento = intento);
            }
        } catch (OutOfMemoryError e) {
            Nodo.raiz = null;
            System.out.println(argumento == intento);
        }
    }
}

// Salida esperada:
// heap limitado
// false
