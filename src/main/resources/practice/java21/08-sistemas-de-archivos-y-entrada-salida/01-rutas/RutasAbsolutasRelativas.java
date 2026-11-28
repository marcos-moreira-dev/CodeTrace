// Tema:
// Una ruta relativa se interpreta desde el directorio de trabajo. Una absoluta incluye su raíz.
// No imprimimos rutas específicas de una máquina: consultamos sus propiedades.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 RutasAbsolutasRelativas.java
// Ejecutar: java RutasAbsolutasRelativas

import java.nio.file.Path;

public class RutasAbsolutasRelativas {
    public static void main(String[] args) throws Exception {
        Path relativa = Path.of("datos", "ventas.txt");
        Path absoluta = relativa.toAbsolutePath();
        System.out.println("Relativa: " + !relativa.isAbsolute());
        System.out.println("Absoluta: " + absoluta.isAbsolute());
        System.out.println("Padre: " + relativa.getParent());
        System.out.println("Raiz presente: " + (absoluta.getRoot() != null));
    }
}

// Salida esperada:
// Relativa: true
// Absoluta: true
// Padre: datos
// Raiz presente: true
