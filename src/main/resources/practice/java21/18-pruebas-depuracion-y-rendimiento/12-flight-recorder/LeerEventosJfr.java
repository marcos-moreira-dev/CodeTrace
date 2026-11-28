// Tema:
// RecordingFile lee eventos de una grabación. Se genera una grabación propia para que el ejercicio sea independiente; se filtra por nombre y se extrae el campo sin depender de fechas ni duración.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 LeerEventosJfr.java
// Ejecutar: java LeerEventosJfr

import jdk.jfr.*;
import jdk.jfr.consumer.*;
import java.nio.file.*;
import java.util.List;

public class LeerEventosJfr {
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
            int encontrados = 0;
            try (RecordingFile archivo = new RecordingFile(ruta)) {
                while (archivo.hasMoreEvents()) {
                    RecordedEvent evento = archivo.readEvent();
                    if (evento.getEventType().getName().equals("codetrace.Pedido")) {
                        System.out.println("Cantidad: " + evento.getInt("cantidad"));
                        encontrados++;
                    }
                }
            }
            System.out.println("Eventos propios: " + encontrados);
        } finally { Files.deleteIfExists(ruta); }
    }
}

// Salida esperada:
// Cantidad: 12
// Eventos propios: 1
