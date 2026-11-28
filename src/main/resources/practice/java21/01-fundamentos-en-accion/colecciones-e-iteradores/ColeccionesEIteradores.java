// Compilar (desde esta carpeta): javac -encoding UTF-8 ColeccionesEIteradores.java
// Ejecutar: java ColeccionesEIteradores

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ColeccionesEIteradores.java
 *   java ColeccionesEIteradores
 * Salida esperada:
 *   Con repeticiones: [OK, OK, PENDIENTE]
 *   Sin repeticiones: [OK, PENDIENTE]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
