// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado

package codetrace.practica.estructuras.grafoponderado;

/**
 * Cliente grafo ponderado: pesos y direcciones NO son lo mismo.
 *
 * <p>Se pueden registrar latencias en cada arco. Las aristas A->B y
 * B->A pueden tener costos distintos. En el algoritmo Dijkstra se
 * exigirán pesos no negativos, mientras Bellman-Ford los admite con
 * excepciones conocidas. El modelo es independiente del algoritmo.
 */
public final class MainGrafoPonderado {
    public static void main(String[] args) {
        var g=new GrafoPonderado(3);
        g.conectar(0,1,4.5);g.conectar(0,2,7);g.conectar(1,0,6);
        if(g.vecinos(0).size()!=2||g.vecinos(1).size()!=1
                ||g.vecinos(2).size()!=0)throw new AssertionError("Direcciones");
        System.out.println("Aristas de origen: "+g.vecinos(0));
    }
}

/*
 * Salida esperada de consola:
 *   Aristas de origen: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
