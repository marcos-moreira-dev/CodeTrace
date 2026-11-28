// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.1.3; ejemplo 8.3.1.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . DatosPersistentes.java PersistenciaTransient.java
// Ejecutar: java PersistenciaTransient
// Comandos desde la carpeta de la unidad.

import java.io.*;
public class PersistenciaTransient {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream memoria = new ByteArrayOutputStream();
        try (ObjectOutputStream salida = new ObjectOutputStream(memoria)) {
            salida.writeObject(new DatosPersistentes());
        }
        try (ObjectInputStream entrada = new ObjectInputStream(
                new ByteArrayInputStream(memoria.toByteArray()))) {
            DatosPersistentes d = (DatosPersistentes) entrada.readObject();
            System.out.println(d.permanente);
            System.out.println(d.temporal);
        }
    }
}

// Salida esperada:
// 8
// 0
