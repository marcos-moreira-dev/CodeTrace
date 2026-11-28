// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainColaPrioridad.java MinHeapDidactico.java
// Ejecutar: java codetrace.practica.heaps.MainColaPrioridad
package codetrace.practica.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Montículo mínimo sobre un arreglo, sin java.util.PriorityQueue.
 *
 * <p>La relación padre-hijos depende del índice: padre=(i-1)/2,
 * hijos=2*i+1 y 2*i+2. El invariante garantiza que cada padre sea
 * menor o igual a sus hijos; no significa que el arreglo esté ordenado.
 */
public final class MinHeapDidactico {
    private final List<Integer> datos = new ArrayList<>();

    public void insertar(int numero) {
        datos.add(numero);
        int i = datos.size() - 1;
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (datos.get(padre) <= datos.get(i)) break;
            intercambiar(i, padre);
            i = padre;
        }
    }

    public int extraerMinimo() {
        if (datos.isEmpty()) throw new IllegalStateException("Heap vacío");
        int primero = datos.get(0);
        int ultimo = datos.remove(datos.size() - 1);
        if (!datos.isEmpty()) {
            datos.set(0, ultimo);
            int i = 0;
            while (true) {
                int menor = i, izq = 2 * i + 1, der = izq + 1;
                if (izq < datos.size() && datos.get(izq) < datos.get(menor)) menor = izq;
                if (der < datos.size() && datos.get(der) < datos.get(menor)) menor = der;
                if (menor == i) break;
                intercambiar(i, menor);
                i = menor;
            }
        }
        return primero;
    }

    public boolean vacio() { return datos.isEmpty(); }

    private void intercambiar(int a, int b) {
        int tmp = datos.get(a);
        datos.set(a, datos.get(b));
        datos.set(b, tmp);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainColaPrioridad.java MinHeapDidactico.java
 *   java codetrace.practica.heaps.MainColaPrioridad
 * Nota: MinHeapDidactico.java es un componente; la salida corresponde al cliente MainColaPrioridad.java.
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 *   9
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
