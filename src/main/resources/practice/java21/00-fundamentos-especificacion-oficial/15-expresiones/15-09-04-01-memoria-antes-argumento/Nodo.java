// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.9.4; ejemplo 15.9.4-1
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
