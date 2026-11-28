// Tema:
// Selección relacional: conserva las filas que cumplen un predicado, sin modificar las columnas. Los datos son ficticios y residen en memoria.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 FiltrarRegistros.java
// Ejecutar: java FiltrarRegistros

public class FiltrarRegistros {
    public static void main(String[] args) throws Exception {
        String[] nombres = {"pan", "leche", "arroz"};
        int[] cantidades = {12, 0, 7};
        for (int i = 0; i < nombres.length; i++)
            if (cantidades[i] > 0) System.out.println(nombres[i] + ": " + cantidades[i]);
    }
}

// Salida esperada:
// pan: 12
// arroz: 7
