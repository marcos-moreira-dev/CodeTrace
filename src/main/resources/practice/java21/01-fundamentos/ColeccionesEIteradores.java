// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" ColeccionesEIteradores

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Cliente de List, Set e Iterator.
 *
 * <p>List conserva posiciones y puede repetir valores. Set exige
 * unicidad; LinkedHashSet conserva además el orden de inserción.
 * Cuando se elimina mientras se recorre un ArrayList, debe utilizarse
 * Iterator.remove (o removeIf) para respetar el contrato del iterador.
 * Un barrido de n elementos es O(n); no supone ordenar ni buscar.
 */
public class ColeccionesEIteradores {
    public static void main(String[] args) {
        List<String> eventos = new ArrayList<>(
            List.of("OK", "ERROR", "OK", "PENDIENTE"));
        Iterator<String> iterador = eventos.iterator();
        while (iterador.hasNext()) {
            if (iterador.next().equals("ERROR")) iterador.remove();
        }
        Set<String> unicos = new LinkedHashSet<>(eventos);
        System.out.println("Con repeticiones: " + eventos);
        System.out.println("Sin repeticiones: " + unicos);
    }
}
