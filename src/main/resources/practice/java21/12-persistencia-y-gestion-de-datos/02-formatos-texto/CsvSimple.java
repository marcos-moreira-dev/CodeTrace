// Tema:
// CSV de subconjunto explícito: dos campos, nombre alfabético y cantidad entera no negativa. Se rechazan comillas, comas dentro de campos y saltos internos; no es un parser CSV general. El archivo usa UTF-8.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 CsvSimple.java MainCsv.java
// Ejecutar: java MainCsv

public class CsvSimple {
    public static String codificar(String nombre, int cantidad) {
        if (!nombre.matches("[A-Za-z]{1,30}") || cantidad < 0)
            throw new IllegalArgumentException("Campo invalido");
        return nombre + "," + cantidad;
    }

    public static String[] analizar(String linea) {
        String[] campos = linea.split(",", -1);
        if (campos.length != 2 || !campos[0].matches("[A-Za-z]{1,30}")
                || !campos[1].matches("[0-9]{1,10}"))
            throw new IllegalArgumentException("Fila invalida");
        Integer.parseInt(campos[1]); // Detecta valores fuera del rango int.
        return campos;
    }
}

// Salida esperada:
// pan: 12
// leche: 7
// Fila rechazada
