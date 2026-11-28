// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . EventoPartida.java MainCadenaResponsabilidad.java ManejadorEquipamiento.java ManejadorEvento.java ManejadorObjetivo.java ManejadorReconocimiento.java TipoEvento.java
// Ejecutar: java codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
// Dominio: Arena Táctica; reportes de una ronda ficticia por equipos.
// Glosario: rehén = personaje virtual a rescatar; MP9 = subfusil del juego.
// Minimapa: pequeña vista del mapa; no se enseña uso real de armas.
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
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EventoPartida.java MainCadenaResponsabilidad.java ManejadorEquipamiento.java ManejadorEvento.java ManejadorObjetivo.java ManejadorReconocimiento.java TipoEvento.java
 *   java codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
 * Nota: ManejadorEvento.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: rehén virtual rescatado
 *   Reconocimiento: rival visto en el minimapa
 *   Equipamiento: MP9 registrado en inventario virtual
 *   Sin responsable: señal desconocida
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
