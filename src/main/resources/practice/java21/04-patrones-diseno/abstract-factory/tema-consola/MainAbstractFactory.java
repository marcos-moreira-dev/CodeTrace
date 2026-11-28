// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . Boton.java Dialogo.java Fabrica.java FabricaClara.java FabricaOscura.java MainAbstractFactory.java
// Ejecutar: java codetrace.practica.patrones.abstractfactory.MainAbstractFactory
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Interfaz del torneo, con paneles claros y oscuros.
// Patrón abstract-factory: Crear familias de componentes compatibles sin conocer sus clases concretas.
// Glosario: HUD: indicadores gráficos de la partida mostrados en pantalla.

package codetrace.practica.patrones.abstractfactory;

import java.util.List;

/**
 * Cliente Abstract Factory: familias de componentes de un mismo tema.
 *
 * <p>La aplicación consume Boton y Dialogo sin conocer sus variantes
 * concretas. Cambiar la fábrica intercambia la familia COMPLETA.
 * En un programa de consola "pintar" devuelve texto en vez de abrir
 * JavaFX. El ejemplo enseña familias compatibles sin forzar MVC.
 */
public final class MainAbstractFactory {
    private static String pantalla(Fabrica fabrica) {
        return fabrica.boton().pintar()+" / "+fabrica.dialogo().pintar();
    }
    public static void main(String[] args) {
        var resultados=List.of(
            pantalla(new FabricaClara()),
            pantalla(new FabricaOscura()));
        if(resultados.get(0).equals(resultados.get(1)))
            throw new AssertionError("Fábricas indistinguibles");
        resultados.forEach(System.out::println);
    }
}

/*
 * Salida esperada de consola:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * Las dos fábricas producen familias coherentes y distinguibles.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . Boton.java Dialogo.java Fabrica.java FabricaClara.java FabricaOscura.java MainAbstractFactory.java
 *   java codetrace.practica.patrones.abstractfactory.MainAbstractFactory
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
