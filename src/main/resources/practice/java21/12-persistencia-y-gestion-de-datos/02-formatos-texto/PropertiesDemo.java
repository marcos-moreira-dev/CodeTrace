// Tema:
// Properties almacena configuración clave-valor. Usamos Reader/Writer UTF-8 explícito y no comparamos los comentarios con fecha que store genera.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 PropertiesDemo.java
// Ejecutar: java PropertiesDemo

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesDemo {
    public static void main(String[] args) throws Exception {
        Path ruta = Files.createTempFile("config-", ".properties");
        try {
            Properties config = new Properties();
            config.setProperty("sucursal", "Norte");
            config.setProperty("limite", "20");
            try (var salida = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
                config.store(salida, "Configuracion de prueba");
            }
            Properties leida = new Properties();
            try (var entrada = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
                leida.load(entrada);
            }
            System.out.println(leida.getProperty("sucursal"));
            System.out.println(Integer.parseInt(leida.getProperty("limite")));
        } finally { Files.deleteIfExists(ruta); }
    }
}

// Salida esperada:
// Norte
// 20
