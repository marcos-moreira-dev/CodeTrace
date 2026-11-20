// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA de java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" codetrace.practica.algoritmos.limites.MainLimitesOrdenados

package codetrace.practica.algoritmos.limites;

/**
 * Cliente: buscar rango de iguales y posicion de insercion.
 * [1,3,3,3,5,9], objetivo 3 => lower=1, upper=4. El rango
 * semiabierto [1,4) contiene tres elementos iguales.
 * Buscar 4, ausente, da [4,4): sitio correcto para insertar.
 * Relacion con paginacion por cursor y consultas de rango.
 */
public final class MainLimitesOrdenados{
    public static void main(String[] args){
        int[] a={1,3,3,3,5,9};
        int l=LimitesOrdenados.lowerBound(a,3);
        int u=LimitesOrdenados.upperBound(a,3);
        int aus=LimitesOrdenados.lowerBound(a,4);
        if(l!=1||u!=4||aus!=4||LimitesOrdenados.upperBound(a,4)!=4)
            throw new AssertionError("Rangos");
        System.out.println("Valor 3: ["+l+", "+u+")");
        System.out.println("Insertar 4 en indice: "+aus);
    }
}
/*
 * Salida esperada:
 * Valor 3: [1, 4)
 * Insertar 4 en indice: 4
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.algoritmos.limites.MainLimitesOrdenados
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
