// Tema:
// La creación de una instancia reserva memoria antes de evaluar los argumentos del
// constructor. Si no hay memoria suficiente, esos argumentos pueden no llegar a evaluarse.
// Ejecutar solo con el límite de heap indicado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nodo.java MemoriaObjeto.java
// Ejecutar: java -Xmx16m -Xint MemoriaObjeto
// Comandos desde la carpeta de la unidad.

class Nodo {
    static Nodo raiz;
    Nodo anterior;
    int numero;
    Nodo(int n) { numero = n; anterior = raiz; raiz = this; }
}

// Salida esperada:
// heap limitado
// false
