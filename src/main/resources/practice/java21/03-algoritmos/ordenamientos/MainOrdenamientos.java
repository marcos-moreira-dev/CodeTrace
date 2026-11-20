// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" codetrace.practica.ordenamientos.MainOrdenamientos

package codetrace.practica.ordenamientos;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Cliente de COMPARATIVA DE CINCO ORDENAMIENTOS (libro cap. 37).
 *
 * <p>Se usa el mismo arreglo clonado para insertion, bubble
 * optimizado, merge, heap y quick, evitando comparaciones invalidas
 * entre entradas diferentes. Todos deben producir los mismos datos.
 * <p>El ordenamiento elegido para produccion exige ademas comparar:
 * estabilidad, memoria adicional, adaptividad, in-place, mejor/peor
 * caso y caracteristicas de la entrada. Una sola prueba funcional
 * NO demuestra el costo asintotico ni un benchmark JIT fiable.
 */
public final class MainOrdenamientos {
    public static void main(String[] args){
        int[] entrada={9,3,8,1,7,2,5};
        Map<String,Consumer<int[]>> metodos=new LinkedHashMap<>();
        metodos.put("Insercion",Ordenadores::insercion);
        metodos.put("Burbuja",Ordenadores::burbuja);
        metodos.put("Mezcla",Ordenadores::mezcla);
        metodos.put("HeapSort",Ordenadores::heap);
        metodos.put("QuickSort",Ordenadores::rapido);
        int[] esperado={1,2,3,5,7,8,9};
        for(var e:metodos.entrySet()){
            int[] copia=entrada.clone();
            e.getValue().accept(copia);
            if(!Arrays.equals(copia,esperado))
                throw new AssertionError("Fallo en "+e.getKey());
            System.out.println(e.getKey()+": "+Arrays.toString(copia));
        }
        for(var funcion:metodos.values()){
            int[] vacio={};
            funcion.accept(vacio);
            int[] repetidos={3,1,3,1};
            funcion.accept(repetidos);
            if(!Arrays.equals(repetidos,new int[]{1,1,3,3}))
                throw new AssertionError("Repetidos");
        }
    }
}
/*
 * Salida esperada (cinco lineas):
 * Insercion: [1, 2, 3, 5, 7, 8, 9]
 * Burbuja: [1, 2, 3, 5, 7, 8, 9]
 * Mezcla: [1, 2, 3, 5, 7, 8, 9]
 * HeapSort: [1, 2, 3, 5, 7, 8, 9]
 * QuickSort: [1, 2, 3, 5, 7, 8, 9]
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.ordenamientos.MainOrdenamientos
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
