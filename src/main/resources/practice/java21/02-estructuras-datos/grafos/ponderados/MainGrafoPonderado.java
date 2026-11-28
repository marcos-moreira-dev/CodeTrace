// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . GrafoPonderado.java MainGrafoPonderado.java
// Ejecutar: java codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado

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
 *   Aristas de origen: [Arista[destino=1, peso=4.5], Arista[destino=2, peso=7.0]]
 * El record Arista imprime sus componentes; no aparece el arco inverso 1→0.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GrafoPonderado.java MainGrafoPonderado.java
 *   java codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado
 * Salida esperada:
 *   Aristas de origen: [Arista[destino=1, peso=4.5], Arista[destino=2, peso=7.0]]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
