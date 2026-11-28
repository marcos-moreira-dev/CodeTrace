// Tema:
// Un índice en memoria asocia clave a desplazamiento en bytes. Se construye mediante un recorrido inicial y después permite acceso directo. Se restringe a registros fijos de dos int y claves únicas; no es un índice persistente ni B-tree.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 IndiceArchivo.java MainIndice.java
// Ejecutar: java MainIndice

import java.io.*;
import java.nio.file.*;

public class MainIndice {
    public static void main(String[] args) throws Exception {
        Path ruta = Files.createTempFile("indice-", ".bin");
        try {
            try (DataOutputStream salida = new DataOutputStream(Files.newOutputStream(ruta))) {
                salida.writeInt(101); salida.writeInt(12);
                salida.writeInt(102); salida.writeInt(7);
                salida.writeInt(103); salida.writeInt(20);
            }
            try (IndiceArchivo indice = new IndiceArchivo(ruta)) {
                System.out.println("102: " + indice.buscar(102).orElseThrow());
                System.out.println("999 existe: " + indice.buscar(999).isPresent());
            }
        } finally { Files.deleteIfExists(ruta); }
    }
}

// Salida esperada:
// 102: 7
// 999 existe: false
