// Tema:
// resolve combina una base y un segmento relativo. relativize obtiene el camino entre rutas compatibles.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 ResolverRuta.java
// Ejecutar: java ResolverRuta

import java.nio.file.Path;

public class ResolverRuta {
    public static void main(String[] args) throws Exception {
        Path base = Path.of("tienda");
        Path destino = base.resolve("ventas.txt");
        System.out.println(destino.getFileName());
        System.out.println(base.relativize(destino));
    }
}

// Salida esperada:
// ventas.txt
// ventas.txt
