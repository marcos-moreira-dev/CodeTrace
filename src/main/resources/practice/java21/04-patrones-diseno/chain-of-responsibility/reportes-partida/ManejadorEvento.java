// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practica-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practica-java" codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
package codetrace.practica.patrones.cadena;

import java.util.Objects;

/**
 * Handler del patrón Chain of Responsibility (GoF).
 *
 * <p>Cada eslabón atiende el evento o delega al siguiente. La cadena no
 * depende de condicionales dentro del cliente y permite añadir nuevas
 * responsabilidades sin cambiar los handlers existentes.</p>
 *
 * <p>Una petición se procesa por el primer handler compatible.
 * No es un pipeline de validaciones que deba ejecutar todos los pasos.</p>
 */
public abstract class ManejadorEvento {
    private ManejadorEvento siguiente;

    /** Encadena el siguiente responsable y lo devuelve para continuar enlazando. */
    public final ManejadorEvento enlazar(ManejadorEvento siguiente) {
        this.siguiente = Objects.requireNonNull(siguiente, "siguiente");
        return siguiente;
    }

    /** Cada implementación decide si atiende o transmite la solicitud. */
    public abstract String atender(EventoPartida evento);

    protected final String delegar(EventoPartida evento) {
        return siguiente == null
                ? "Sin responsable: " + evento.detalle()
                : siguiente.atender(evento);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
 * Nota: ManejadorEvento.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: asegurar el sitio B
 *   Reconocimiento: enemigo en pasillo central
 *   Apoyo: solicitar munición adicional
 *   Sin responsable: señal desconocida
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
