// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.unionfind;

/**
 * Conjuntos disjuntos DSU (Disjoint Set Union).
 * Compresión de caminos + unión por tamaño: coste amortizado
 * O(alfa(n)), prácticamente constante para entradas reales.
 * No confundir con una estructura para hallar caminos de un grafo.
 */
public final class UnionFind {
    private final int[] padre, tamanio;
    private int componentes;
    public UnionFind(int n) {
        if(n<=0)throw new IllegalArgumentException("n");
        padre=new int[n]; tamanio=new int[n]; componentes=n;
        for(int i=0;i<n;i++){padre[i]=i;tamanio[i]=1;}
    }
    public int representante(int x) {
        if(padre[x]!=x)padre[x]=representante(padre[x]);
        return padre[x];
    }
    public boolean unir(int a,int b) {
        int ra=representante(a),rb=representante(b);
        if(ra==rb)return false;
        if(tamanio[ra]<tamanio[rb]){int t=ra;ra=rb;rb=t;}
        padre[rb]=ra;tamanio[ra]+=tamanio[rb];componentes--;
        return true;
    }
    public boolean conectados(int a,int b) {
        return representante(a)==representante(b);
    }
    public int componentes() { return componentes; }
}
