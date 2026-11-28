// Tema:
// Proyección relacional: elegir columnas. La proyección clásica elimina duplicados; mostramos nombres únicos conservando el orden de aparición.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 ProyectarCampos.java
// Ejecutar: java ProyectarCampos

import java.util.LinkedHashSet;

public class ProyectarCampos {
    public static void main(String[] args) throws Exception {
        String[][] filas = {{"pan", "Norte"}, {"leche", "Sur"}, {"pan", "Sur"}};
        LinkedHashSet<String> nombres = new LinkedHashSet<>();
        for (String[] fila : filas) nombres.add(fila[0]);
        for (String nombre : nombres) System.out.println(nombre);
    }
}

// Salida esperada:
// pan
// leche
