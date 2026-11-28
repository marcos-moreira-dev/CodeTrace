// Tema:
// JDK Flight Recorder registra eventos de la JVM y personalizados. EventFactory define un evento dinámico sin clases auxiliares. La grabación se detiene y vuelca a un archivo temporal; no se perfila ningún proceso ajeno.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 GrabarJfr.java
// Ejecutar: java GrabarJfr

import jdk.jfr.*;
import jdk.jfr.consumer.*;
import java.nio.file.*;
import java.util.List;

public class GrabarJfr {
    static void grabar(Path ruta) throws Exception {
        EventFactory fabrica = EventFactory.create(
                List.of(new AnnotationElement(Name.class, "codetrace.Pedido")),
                List.of(new ValueDescriptor(int.class, "cantidad")));
        fabrica.register();
        try (Recording grabacion = new Recording()) {
            grabacion.enable("codetrace.Pedido").withoutThreshold();
            grabacion.start();
            Event evento = fabrica.newEvent();
            evento.set(0, 12);
            evento.commit();
            grabacion.stop();
            grabacion.dump(ruta);
        } finally { fabrica.unregister(); }
    }

    public static void main(String[] args) throws Exception {
        Path ruta = Files.createTempFile("practica-", ".jfr");
        try {
            grabar(ruta);
            System.out.println("Grabacion creada: " + (Files.size(ruta) > 0));
        } finally { Files.deleteIfExists(ruta); }
    }
}

// Salida esperada:
// Grabacion creada: true
