// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . InsercionBinaria.java MainInsercionBinaria.java
// Ejecutar: java codetrace.practica.algoritmos.insercionbinaria.MainInsercionBinaria

package codetrace.practica.algoritmos.insercionbinaria;
import java.util.Arrays;

/**
 * Cliente Insercion Binaria: no confundir reducir COMPARACIONES
 * con reducir MOVIMIENTOS. Ubicar el hueco en O(log i) no evita
 * desplazar hasta i elementos en un arreglo. Por tanto no es un
 * algoritmo general O(n log n). Casos: repetidos, vacio, ordenado.
 */
public final class MainInsercionBinaria{
    public static void main(String[] args){
        int[] datos={5,1,3,3,2};
        InsercionBinaria.ordenar(datos);
        if(!Arrays.equals(datos,new int[]{1,2,3,3,5}))
            throw new AssertionError("Insercion binaria");
        InsercionBinaria.ordenar(new int[0]);
        System.out.println("Insercion binaria: "+Arrays.toString(datos));
    }
}
/*
 * Salida esperada:
 * Insercion binaria: [1, 2, 3, 3, 5]
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . InsercionBinaria.java MainInsercionBinaria.java
 *   java codetrace.practica.algoritmos.insercionbinaria.MainInsercionBinaria
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
