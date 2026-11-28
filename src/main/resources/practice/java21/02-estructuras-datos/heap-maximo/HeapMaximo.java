// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . HeapMaximo.java MainHeapMaximo.java
// Ejecutar: java codetrace.practica.estructuras.heapmaximo.MainHeapMaximo

package codetrace.practica.estructuras.heapmaximo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Max heap binario almacenado en arreglo.
 * Padre(i)=(i-1)/2, hijos=2i+1,2i+2.
 * Invariante: cada padre es >= sus hijos; no es BST.
 * Insertar/extraer O(log n), máximo O(1).
 */
public final class HeapMaximo {
    private final List<Integer> datos=new ArrayList<>();
    public void insertar(int x) {
        datos.add(x);
        int i=datos.size()-1;
        while(i>0) {
            int p=(i-1)/2;
            if(datos.get(p)>=datos.get(i))break;
            intercambiar(i,p);i=p;
        }
    }
    public int extraer() {
        if(datos.isEmpty())throw new NoSuchElementException("Heap vacío");
        int salida=datos.get(0);
        int ultimo=datos.remove(datos.size()-1);
        if(!datos.isEmpty()) {
            datos.set(0,ultimo);
            int i=0;
            while(true) {
                int mayor=i,a=2*i+1,b=a+1;
                if(a<datos.size()&&datos.get(a)>datos.get(mayor))mayor=a;
                if(b<datos.size()&&datos.get(b)>datos.get(mayor))mayor=b;
                if(mayor==i)break;
                intercambiar(i,mayor);i=mayor;
            }
        }
        return salida;
    }
    public boolean vacio() { return datos.isEmpty(); }
    private void intercambiar(int a,int b) {
        int x=datos.get(a);datos.set(a,datos.get(b));datos.set(b,x);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . HeapMaximo.java MainHeapMaximo.java
 *   java codetrace.practica.estructuras.heapmaximo.MainHeapMaximo
 * Nota: HeapMaximo.java es un componente; la salida corresponde al cliente MainHeapMaximo.java.
 * Salida esperada:
 *   Max heap verificado
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
