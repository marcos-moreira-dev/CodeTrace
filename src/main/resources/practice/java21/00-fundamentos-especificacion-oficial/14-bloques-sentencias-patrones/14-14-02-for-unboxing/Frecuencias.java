// Tema:
// El for mejorado puede convertir cada elemento de un Iterable a una variable primitiva
// mediante unboxing. El tipo de la variable determina las conversiones necesarias.
// Compilar: javac --release 21 -encoding UTF-8 -d . Frecuencias.java
// Ejecutar: java Frecuencias
// Comandos desde la carpeta de la unidad.

import java.util.Map;
import java.util.TreeMap;
public class Frecuencias {
    public static void main(String[] args) {
        Map<String, Integer> conteo = new TreeMap<>();
        conteo.put("azul", 3);
        conteo.put("rojo", 1);
        double total = 0;
        for (int cantidad : conteo.values()) total += cantidad;
        for (var entrada : conteo.entrySet()) {
            System.out.println(entrada.getKey() + " " + entrada.getValue() / total);
        }
    }
}

// Salida esperada:
// azul 0.75
// rojo 0.25
