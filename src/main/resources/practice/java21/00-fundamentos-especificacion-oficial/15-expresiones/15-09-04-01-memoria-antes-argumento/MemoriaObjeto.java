// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.9.4; ejemplo 15.9.4-1
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
