// Tema:
// Normalizar y comprobar startsWith por componentes evita escapes léxicos del directorio. El ejemplo crea un árbol temporal propio sin enlaces; no basta contra enlaces simbólicos, cambios concurrentes ni todos los riesgos de un sistema de archivos hostil.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 ValidarRuta.java
// Ejecutar: java ValidarRuta

import java.nio.file.*;

public class ValidarRuta {
    public static void main(String[] args) throws Exception {
        Path raiz = Files.createTempDirectory("rutas-").toAbsolutePath().normalize();
        try {
            for (String entrada : new String[]{"ventas.txt", "../afuera.txt"}) {
                Path relativa = Path.of(entrada);
                Path destino = raiz.resolve(relativa).normalize();
                boolean permitida = !relativa.isAbsolute() && destino.startsWith(raiz) && !destino.equals(raiz);
                System.out.println(entrada + ": " + permitida);
            }
        } finally { Files.deleteIfExists(raiz); }
    }
}

// Salida esperada:
// ventas.txt: true
// ../afuera.txt: false
