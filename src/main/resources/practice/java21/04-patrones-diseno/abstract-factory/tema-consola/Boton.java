// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . Boton.java Dialogo.java Fabrica.java FabricaClara.java FabricaOscura.java MainAbstractFactory.java
// Ejecutar: java codetrace.practica.patrones.abstractfactory.MainAbstractFactory
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Interfaz del torneo, con paneles claros y oscuros.
// Patrón abstract-factory: Crear familias de componentes compatibles sin conocer sus clases concretas.
// Glosario: HUD: indicadores gráficos de la partida mostrados en pantalla.
// Un archivo por contrato o implementación del patrón Abstract Factory.
// Salida esperada: [ OK ] / Ventana clara; < OK > / Ventana oscura.
package codetrace.practica.patrones.abstractfactory;

/** Producto abstracto Boton, definido por su propio contrato. */
@FunctionalInterface
public interface Boton {
    String pintar();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . Boton.java Dialogo.java Fabrica.java FabricaClara.java FabricaOscura.java MainAbstractFactory.java
 *   java codetrace.practica.patrones.abstractfactory.MainAbstractFactory
 * Nota: Boton.java es un componente; la salida corresponde al cliente MainAbstractFactory.java.
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
