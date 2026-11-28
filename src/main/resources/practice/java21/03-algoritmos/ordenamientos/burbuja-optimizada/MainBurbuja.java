// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . BurbujaOptimizada.java MainBurbuja.java
// Ejecutar: java codetrace.practica.algoritmos.burbuja.MainBurbuja

package codetrace.practica.algoritmos.burbuja;
import java.util.Arrays;

/**
 * Cliente Burbuja optimizada: compara datos ordenados y al reves.
 * Una pasada sin intercambios permite terminar tras n-1
 * comparaciones. Los intercambios estrictamente por > preservan
 * estabilidad. Las comparaciones son una medida reproducible;
 * no confundirla con milisegundos de ejecucion.
 */
public final class MainBurbuja {
    public static void main(String[] args){
        int[] asc={1,2,3,4,5}, desc={5,4,3,2,1};
        int mejor=BurbujaOptimizada.ordenar(asc);
        int peor=BurbujaOptimizada.ordenar(desc);
        if(mejor!=4||peor!=10||!Arrays.equals(desc,asc))
            throw new AssertionError("Burbuja");
        System.out.println("Ordenado: "+Arrays.toString(asc));
        System.out.println("Comparaciones: mejor="+mejor+", peor="+peor);
    }
}
/*
 * Salida esperada:
 * Ordenado: [1, 2, 3, 4, 5]
 * Comparaciones: mejor=4, peor=10
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . BurbujaOptimizada.java MainBurbuja.java
 *   java codetrace.practica.algoritmos.burbuja.MainBurbuja
 * Salida esperada:
 *   Ordenado: [1, 2, 3, 4, 5]
 *   Comparaciones: mejor=4, peor=10
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
