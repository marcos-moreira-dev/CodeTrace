// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Consulta de estadísticas de un torneo ficticio.
// Patrón builder: Construir solicitudes gradualmente con datos opcionales.
// Glosario: Estadística: resultado agregado de partidas; torneo: competición por equipos.
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.builder.MainBuilder
 * Nota: Solicitud.java es un componente; la salida corresponde al cliente MainBuilder.java.
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
