// Tema:
// transient excluye un campo de la serialización predeterminada de objetos. Al deserializar,
// ese campo recibe su valor predeterminado si no se restaura explícitamente.
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
