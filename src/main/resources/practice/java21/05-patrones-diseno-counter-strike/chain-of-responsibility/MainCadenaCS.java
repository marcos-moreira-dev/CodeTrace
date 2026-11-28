// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AvisoRonda.java MainCadenaCS.java ManejadorAviso.java ManejadorInventario.java ManejadorReconocimiento.java ManejadorRescate.java TipoAviso.java
// Ejecutar: java codetrace.practica.counterstrike.cadena.MainCadenaCS
// Dominio: Counter-Strike — avisos de una ronda enviados a responsables.
// Patrón: Chain of Responsibility. Cada componente tiene su propia responsabilidad.
// Glosario: Rehén = personaje virtual que se rescata; MP9 = nombre de subfusil en el inventario del videojuego; minimapa = vista pequeña del escenario.
package codetrace.practica.counterstrike.cadena;

import java.util.List;

/** Cliente: sabe a quién enviar el aviso, no quién acabará resolviéndolo. */
public final class MainCadenaCS {
    public static void main(String[] args) {
        ManejadorAviso primero = new ManejadorRescate();
        // El orden se decide una vez: rescate -> reconocimiento -> inventario.
        primero.enlazar(new ManejadorReconocimiento())
               .enlazar(new ManejadorInventario());

        // List.of crea cuatro avisos de prueba. No es una lista de responsables.
        List<AvisoRonda> avisos = List.of(
                new AvisoRonda(TipoAviso.REHEN, "rehén virtual liberado"),
                new AvisoRonda(TipoAviso.RIVAL, "rival marcado en el minimapa"),
                new AvisoRonda(TipoAviso.EQUIPAMIENTO, "MP9 registrado como objeto virtual"),
                new AvisoRonda(TipoAviso.OTRO, "evento del servidor no reconocido")
        );

        // Cada posición de 'esperado' corresponde a la misma posición de 'avisos'.
        List<String> esperado = List.of(
                "Rescate: rehén virtual liberado",
                "Reconocimiento: rival marcado en el minimapa",
                "Inventario: MP9 registrado como objeto virtual",
                "Sin responsable: evento del servidor no reconocido"
        );
        // Recorremos los casos; todos entran por el PRIMER responsable.
        for (int i = 0; i < avisos.size(); i++) {
            String respuesta = primero.atender(avisos.get(i));
            if (!respuesta.equals(esperado.get(i)))
                throw new AssertionError("Caso " + i + ": " + respuesta);
            System.out.println(respuesta);
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AvisoRonda.java MainCadenaCS.java ManejadorAviso.java ManejadorInventario.java ManejadorReconocimiento.java ManejadorRescate.java TipoAviso.java
 *   java codetrace.practica.counterstrike.cadena.MainCadenaCS
 * Nota: MainCadenaCS.java pertenece al ejercicio; el punto de entrada es MainCadenaCS.java.
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
