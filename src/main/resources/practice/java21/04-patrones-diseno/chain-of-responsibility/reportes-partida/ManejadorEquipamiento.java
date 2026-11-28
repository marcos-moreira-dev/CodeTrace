// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . EventoPartida.java MainCadenaResponsabilidad.java ManejadorEquipamiento.java ManejadorEvento.java ManejadorObjetivo.java ManejadorReconocimiento.java TipoEvento.java
// Ejecutar: java codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
// Dominio: Arena Táctica; reportes de una ronda ficticia por equipos.
// Glosario: rehén = personaje virtual a rescatar; MP9 = subfusil del juego.
// Minimapa: pequeña vista del mapa; no se enseña uso real de armas.
package codetrace.practica.patrones.cadena;

/** Tercer eslabón: recibe solicitudes de equipamiento o munición. */
public final class ManejadorEquipamiento extends ManejadorEvento {
    @Override
    public String atender(EventoPartida evento) {
        if (evento.tipo() == TipoEvento.EQUIPAMIENTO) {
            return "Equipamiento: " + evento.detalle();
        }
        return delegar(evento);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EventoPartida.java MainCadenaResponsabilidad.java ManejadorEquipamiento.java ManejadorEvento.java ManejadorObjetivo.java ManejadorReconocimiento.java TipoEvento.java
 *   java codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
 * Nota: ManejadorEquipamiento.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: rehén virtual rescatado
 *   Reconocimiento: rival visto en el minimapa
 *   Equipamiento: MP9 registrado en inventario virtual
 *   Sin responsable: señal desconocida
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
