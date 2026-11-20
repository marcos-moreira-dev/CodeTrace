// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA de java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" codetrace.practica.algoritmos.burbuja.MainBurbuja

package codetrace.practica.algoritmos.burbuja;

/**
 * Bubble Sort optimizado por frontera del ultimo intercambio.
 * Invariante: tras una pasada, el sufijo posterior al ultimo
 * intercambio ya esta ordenado. Intercambia SOLO ante > y por ello
 * conserva el orden relativo de claves iguales (estable).
 * Mejor Θ(n), promedio/peor Θ(n²), auxiliar Θ(1).
 */
public final class BurbujaOptimizada {
    private BurbujaOptimizada() {}
    public static int ordenar(int[] a) {
        int comparaciones=0, limite=a.length;
        while(limite>1){
            int ultimo=0;
            for(int j=1;j<limite;j++){
                comparaciones++;
                if(a[j-1]>a[j]){
                    int v=a[j-1];a[j-1]=a[j];a[j]=v;
                    ultimo=j;
                }
            }
            limite=ultimo;
        }
        return comparaciones;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.algoritmos.burbuja.MainBurbuja
 * Nota: BurbujaOptimizada.java es un componente; la salida corresponde al cliente MainBurbuja.java.
 * Salida esperada:
 *   Ordenado: [1, 2, 3, 4, 5]
 *   Comparaciones: mejor=4, peor=10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
