// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA de java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" codetrace.practica.algoritmos.burbuja.MainBurbuja

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
