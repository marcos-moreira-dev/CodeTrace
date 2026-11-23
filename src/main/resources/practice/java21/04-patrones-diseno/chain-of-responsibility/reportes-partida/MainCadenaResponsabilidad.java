// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practica-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practica-java" codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
package codetrace.practica.patrones.cadena;

import java.util.List;

/**
 * Demostración de Chain of Responsibility con reportes de una partida táctica.
 *
 * <p>El cliente conoce el primer eslabón; no necesita preguntar qué clase
 * concreta puede atender cada evento. Cada handler conserva una única
 * responsabilidad y delega las solicitudes que no reconoce.</p>
 *
 * <p>Prueba los tres responsables y la ausencia de uno compatible.</p>
 */
public final class MainCadenaResponsabilidad {
    public static void main(String[] args) {
        ManejadorEvento primero = new ManejadorObjetivo();
        primero.enlazar(new ManejadorReconocimiento())
               .enlazar(new ManejadorEquipamiento());

        List<EventoPartida> eventos = List.of(
                new EventoPartida(TipoEvento.OBJETIVO, "asegurar el sitio B"),
                new EventoPartida(TipoEvento.ENEMIGO, "enemigo en pasillo central"),
                new EventoPartida(TipoEvento.MUNICION, "solicitar munición adicional"),
                new EventoPartida(TipoEvento.OTRO, "señal desconocida")
        );
        List<String> esperado = List.of(
                "Objetivo: asegurar el sitio B",
                "Reconocimiento: enemigo en pasillo central",
                "Apoyo: solicitar munición adicional",
                "Sin responsable: señal desconocida"
        );
        for (int i = 0; i < eventos.size(); i++) {
            String respuesta = primero.atender(eventos.get(i));
            if (!respuesta.equals(esperado.get(i))) {
                throw new AssertionError("Respuesta inesperada: " + respuesta);
            }
            System.out.println(respuesta);
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
 *   java -cp $out codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
 * Nota: MainCadenaResponsabilidad.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: asegurar el sitio B
 *   Reconocimiento: enemigo en pasillo central
 *   Apoyo: solicitar munición adicional
 *   Sin responsable: señal desconocida
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
