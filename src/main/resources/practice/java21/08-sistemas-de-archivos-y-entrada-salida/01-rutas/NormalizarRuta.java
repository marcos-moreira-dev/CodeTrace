// Tema:
// normalize elimina segmentos redundantes de forma léxica, sin acceder al disco.
// No equivale a toRealPath ni resuelve enlaces simbólicos.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 NormalizarRuta.java
// Ejecutar: java NormalizarRuta

import java.nio.file.Path;

public class NormalizarRuta {
    public static void main(String[] args) throws Exception {
        Path ruta = Path.of("datos", ".", "enero", "..", "ventas.txt");
        Path limpia = ruta.normalize();
        System.out.println(limpia.equals(Path.of("datos", "ventas.txt")));
    }
}

// Salida esperada:
// true
