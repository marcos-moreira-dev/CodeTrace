// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Dominio: Arena Táctica; reportes de una ronda ficticia por equipos.
// Glosario: rehén = personaje virtual a rescatar; MP9 = subfusil del juego.
// Minimapa: pequeña vista del mapa; no se enseña uso real de armas.
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
                new EventoPartida(TipoEvento.OBJETIVO, "rehén virtual rescatado"),
                new EventoPartida(TipoEvento.ENEMIGO, "rival visto en el minimapa"),
                new EventoPartida(TipoEvento.EQUIPAMIENTO, "MP9 registrado en inventario virtual"),
                new EventoPartida(TipoEvento.OTRO, "señal desconocida")
        );
        List<String> esperado = List.of(
                "Objetivo: rehén virtual rescatado",
                "Reconocimiento: rival visto en el minimapa",
                "Equipamiento: MP9 registrado en inventario virtual",
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
 *   Objetivo: rehén virtual rescatado
 *   Reconocimiento: rival visto en el minimapa
 *   Equipamiento: MP9 registrado en inventario virtual
 *   Sin responsable: señal desconocida
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
