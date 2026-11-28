// Tema:
// Agrupación: se acumula una cantidad por clave. LinkedHashMap conserva el orden de primera aparición y permite mostrar una salida reproducible.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 AgruparRegistros.java
// Ejecutar: java AgruparRegistros

import java.util.LinkedHashMap;

public class AgruparRegistros {
    public static void main(String[] args) throws Exception {
        String[] productos = {"pan", "leche", "pan"};
        int[] ventas = {3, 2, 4};
        LinkedHashMap<String, Integer> totales = new LinkedHashMap<>();
        for (int i = 0; i < productos.length; i++) {
            String producto = productos[i];
            totales.put(producto, totales.getOrDefault(producto, 0) + ventas[i]);
        }
        for (var grupo : totales.entrySet())
            System.out.println(grupo.getKey() + ": " + grupo.getValue());
    }
}

// Salida esperada:
// pan: 7
// leche: 2
