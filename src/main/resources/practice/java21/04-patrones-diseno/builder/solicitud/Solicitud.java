// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.patrones.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Producto inmutable construido en varios pasos con un Builder.
 * Los campos se copian para que el resultado no cambie si se
 * reutiliza el constructor. El Builder valida antes de construir.
 */
public record Solicitud(String ruta,String metodo,List<String> cabeceras) {
    public Solicitud {
        Objects.requireNonNull(ruta);Objects.requireNonNull(metodo);
        cabeceras=List.copyOf(cabeceras);
    }
    public static final class Builder {
        private String ruta,metodo="GET";
        private final List<String> cabeceras=new ArrayList<>();
        public Builder ruta(String ruta){this.ruta=ruta;return this;}
        public Builder metodo(String metodo){this.metodo=metodo;return this;}
        public Builder cabecera(String valor){cabeceras.add(valor);return this;}
        public Solicitud construir(){
            if(ruta==null||!ruta.startsWith("/")||metodo==null||metodo.isBlank())
                throw new IllegalStateException("Solicitud inválida");
            return new Solicitud(ruta,metodo,cabeceras);
        }
    }
}
