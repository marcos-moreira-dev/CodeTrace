// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.grafosrepresentaciones.MainRepresentaciones

package codetrace.practica.grafosrepresentaciones;

/**
 * Cliente: matriz vs. lista de adyacencia sobre el MISMO grafo.
 *
 * <p>Con V vértices y E aristas, la matriz reserva V^2 casillas,
 * incluso si E es pequeño. La lista emplea O(V+E) memoria.
 * En matriz, consultar una arista es O(1); en esta lista ArrayList,
 * consultar cuesta O(grado) pero enumerar vecinos es natural.
 *
 * <p>Se prueba la misma propiedad con ambas implementaciones para
 * separar el modelo matemático de la representación en memoria.
 */
public final class MainRepresentaciones {
    public static void main(String[] args) {
        MatrizAdyacencia matriz = new MatrizAdyacencia(5, false);
        ListaAdyacencia lista = new ListaAdyacencia(5, false);
        int[][] conexiones = {{0, 1}, {0, 2}, {2, 3}, {3, 4}};
        for (int[] arista : conexiones) {
            matriz.conectar(arista[0], arista[1]);
            lista.conectar(arista[0], arista[1]);
        }
        System.out.println("Matriz 0-2: " + matriz.tieneArista(0, 2));
        System.out.println("Lista 0-2: " + lista.tieneArista(0, 2));
        System.out.println("Vecinos de 2: " + lista.vecinos(2));
        if (matriz.tieneArista(0, 2) != lista.tieneArista(0, 2))
            throw new AssertionError("Representaciones inconsistentes");
    }
}

/*
 * Salida esperada de consola:
 *   Matriz 0-2: 
 *   Lista 0-2: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
