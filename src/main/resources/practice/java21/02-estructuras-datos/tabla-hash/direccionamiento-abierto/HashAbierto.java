// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.hashabierto;

/**
 * Tabla hash con DIRECCIONAMIENTO ABIERTO y probing lineal.
 * Esta unidad contrasta con la tabla de encadenamiento. Se usa
 * capacidad fija sin borrados ni resize: la búsqueda debe detenerse
 * al encontrar celda vacía o después de inspeccionar capacidad.
 * Promedio O(1) con factor de carga bajo; peor O(n).
 */
public final class HashAbierto {
    private final String[] claves;
    private final int[] valores;
    private int cantidad;
    public HashAbierto(int capacidad) {
        if(capacidad<=0)throw new IllegalArgumentException("Capacidad");
        claves=new String[capacidad];valores=new int[capacidad];
    }
    public void poner(String clave,int valor) {
        if(clave==null)throw new IllegalArgumentException("null");
        int inicio=Math.floorMod(clave.hashCode(),claves.length);
        for(int paso=0;paso<claves.length;paso++) {
            int i=(inicio+paso)%claves.length;
            if(claves[i]==null) {
                claves[i]=clave;valores[i]=valor;cantidad++;return;
            }
            if(claves[i].equals(clave)){valores[i]=valor;return;}
        }
        throw new IllegalStateException("Tabla llena");
    }
    public Integer buscar(String clave) {
        int inicio=Math.floorMod(clave.hashCode(),claves.length);
        for(int paso=0;paso<claves.length;paso++) {
            int i=(inicio+paso)%claves.length;
            if(claves[i]==null)return null;
            if(claves[i].equals(clave))return valores[i];
        }
        return null;
    }
    public int cantidad(){return cantidad;}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.estructuras.hashabierto.MainHashAbierto
 * Nota: HashAbierto.java es un componente; la salida corresponde al cliente MainHashAbierto.java.
 * Salida esperada:
 *   Tabla llena controlada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
