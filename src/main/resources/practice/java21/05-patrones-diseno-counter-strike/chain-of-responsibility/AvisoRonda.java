// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AvisoRonda.java MainCadenaCS.java ManejadorAviso.java ManejadorInventario.java ManejadorReconocimiento.java ManejadorRescate.java TipoAviso.java
// Ejecutar: java codetrace.practica.counterstrike.cadena.MainCadenaCS
// Dominio: Counter-Strike — avisos de una ronda enviados a responsables.
// Patrón: Chain of Responsibility. Cada componente tiene su propia responsabilidad.
// Glosario: Rehén = personaje virtual que se rescata; MP9 = nombre de subfusil en el inventario del videojuego; minimapa = vista pequeña del escenario.
package codetrace.practica.counterstrike.cadena;

import java.util.Objects;

/** Información inmutable que viaja entre los responsables. */
public record AvisoRonda(TipoAviso tipo, String detalle) {
    public AvisoRonda {
        Objects.requireNonNull(tipo, "El aviso necesita un tipo");
        if (detalle == null || detalle.isBlank())
            throw new IllegalArgumentException("El aviso necesita una descripción");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AvisoRonda.java MainCadenaCS.java ManejadorAviso.java ManejadorInventario.java ManejadorReconocimiento.java ManejadorRescate.java TipoAviso.java
 *   java codetrace.practica.counterstrike.cadena.MainCadenaCS
 * Nota: AvisoRonda.java pertenece al ejercicio; el punto de entrada es MainCadenaCS.java.
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
