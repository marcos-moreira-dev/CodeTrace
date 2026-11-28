// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AvisoRonda.java MainCadenaCS.java ManejadorAviso.java ManejadorInventario.java ManejadorReconocimiento.java ManejadorRescate.java TipoAviso.java
// Ejecutar: java codetrace.practica.counterstrike.cadena.MainCadenaCS
// Dominio: Counter-Strike — avisos de una ronda enviados a responsables.
// Patrón: Chain of Responsibility. Cada componente tiene su propia responsabilidad.
// Glosario: Rehén = personaje virtual que se rescata; MP9 = nombre de subfusil en el inventario del videojuego; minimapa = vista pequeña del escenario.
package codetrace.practica.counterstrike.cadena;

import java.util.Objects;

/** Handler abstracto. Si no resuelve un aviso, lo entrega al siguiente. */
public abstract class ManejadorAviso {
    private ManejadorAviso siguiente;

    public final ManejadorAviso enlazar(ManejadorAviso nuevo) {
        // Retornamos el nuevo eslabón para poder escribir a.enlazar(b).enlazar(c).
        siguiente = Objects.requireNonNull(nuevo);
        return nuevo;
    }

    public abstract String atender(AvisoRonda aviso);

    protected final String delegar(AvisoRonda aviso) {
        // No inventamos un responsable: la cadena puede terminar sin respuesta.
        return siguiente == null
                ? "Sin responsable: " + aviso.detalle()
                : siguiente.atender(aviso);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AvisoRonda.java MainCadenaCS.java ManejadorAviso.java ManejadorInventario.java ManejadorReconocimiento.java ManejadorRescate.java TipoAviso.java
 *   java codetrace.practica.counterstrike.cadena.MainCadenaCS
 * Nota: ManejadorAviso.java pertenece al ejercicio; el punto de entrada es MainCadenaCS.java.
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
