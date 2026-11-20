// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia en PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: invocar desde el Main del mismo ejercicio

package codetrace.practica.grafostopologico;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Ordenamiento topológico de un DAG con el algoritmo de Kahn.
 *
 * <p>Una arista A -> B significa "A debe ocurrir antes que B".
 * El algoritmo comienza con nodos de grado de entrada cero y
 * elimina sus dependencias. Si quedan nodos sin procesar existe
 * al menos un ciclo y no hay orden topológico posible.
 *
 * <p>Complejidad O(V+E) tiempo y O(V+E) memoria del grafo.
 * Puede haber más de un orden válido para el mismo DAG.
 */
public final class OrdenTopologico {
    private final Map<String, Set<String>> salidas = new LinkedHashMap<>();

    public void tarea(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Tarea");
        salidas.computeIfAbsent(nombre, ignorado -> new LinkedHashSet<>());
    }

    public void antesQue(String previa, String siguiente) {
        tarea(previa);
        tarea(siguiente);
        salidas.get(previa).add(siguiente);
    }

    public List<String> ordenar() {
        Map<String, Integer> grados = new LinkedHashMap<>();
        for (String v : salidas.keySet()) grados.put(v, 0);
        for (Set<String> vecinos : salidas.values())
            for (String destino : vecinos) grados.merge(destino, 1, Integer::sum);

        ArrayDeque<String> cola = new ArrayDeque<>();
        for (var entrada : grados.entrySet())
            if (entrada.getValue() == 0) cola.addLast(entrada.getKey());

        List<String> orden = new ArrayList<>();
        while (!cola.isEmpty()) {
            String actual = cola.removeFirst();
            orden.add(actual);
            for (String destino : salidas.get(actual)) {
                int nuevo = grados.merge(destino, -1, Integer::sum);
                if (nuevo == 0) cola.addLast(destino);
            }
        }
        if (orden.size() != salidas.size())
            throw new IllegalStateException("Dependencias cíclicas: no existe orden");
        return List.copyOf(orden);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafostopologico.MainTopologico
 * Nota: OrdenTopologico.java es un componente; la salida corresponde al cliente MainTopologico.java.
 * Salida esperada:
 *   Orden permitido: [Fuentes, Compilar, Pruebas, Empaquetar, Desplegar]
 *   Ciclo detectado correctamente
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
