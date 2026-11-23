// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practica-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practica-java" codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
package codetrace.practica.patrones.cadena;

/** Segundo eslabón: recibe reportes sobre posición enemiga. */
public final class ManejadorReconocimiento extends ManejadorEvento {
    @Override
    public String atender(EventoPartida evento) {
        if (evento.tipo() == TipoEvento.ENEMIGO) {
            return "Reconocimiento: " + evento.detalle();
        }
        return delegar(evento);
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
 * Nota: ManejadorReconocimiento.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: asegurar el sitio B
 *   Reconocimiento: enemigo en pasillo central
 *   Apoyo: solicitar munición adicional
 *   Sin responsable: señal desconocida
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
