// Tema:
// Cada llamada tiene su propio marco lógico con variables locales. La recursión apila
// llamadas y las retira al retornar; no se mide la pila física.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 PilaLlamadas.java
// Ejecutar: java PilaLlamadas

public class PilaLlamadas {
static void bajar(int n) {
        if (n == 0) return;
        System.out.println("entra " + n);
        bajar(n - 1);
        System.out.println("sale " + n);
    }

    public static void main(String[] args) {
        bajar(3);
    }
}

// Salida esperada:
// entra 3
// entra 2
// entra 1
// sale 1
// sale 2
// sale 3
