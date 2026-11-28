// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . EventoPartida.java MainCadenaResponsabilidad.java ManejadorEquipamiento.java ManejadorEvento.java ManejadorObjetivo.java ManejadorReconocimiento.java TipoEvento.java
// Ejecutar: java codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
// Dominio: Arena Táctica; reportes de una ronda ficticia por equipos.
// Glosario: rehén = personaje virtual a rescatar; MP9 = subfusil del juego.
// Minimapa: pequeña vista del mapa; no se enseña uso real de armas.
package codetrace.practica.patrones.cadena;

import java.util.Objects;

/**
 * Solicitud inmutable que viaja por la cadena de responsables.
 * Cada evento contiene solo el tipo y una descripción concreta.
 */
public record EventoPartida(TipoEvento tipo, String detalle) {
    public EventoPartida {
        Objects.requireNonNull(tipo, "tipo");
        Objects.requireNonNull(detalle, "detalle");
        if (detalle.isBlank()) throw new IllegalArgumentException("Detalle vacío");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EventoPartida.java MainCadenaResponsabilidad.java ManejadorEquipamiento.java ManejadorEvento.java ManejadorObjetivo.java ManejadorReconocimiento.java TipoEvento.java
 *   java codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
 * Nota: EventoPartida.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: rehén virtual rescatado
 *   Reconocimiento: rival visto en el minimapa
 *   Equipamiento: MP9 registrado en inventario virtual
 *   Sin responsable: señal desconocida
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
