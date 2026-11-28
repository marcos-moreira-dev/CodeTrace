// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . EtiquetaInventario.java EtiquetaMarcador.java FabricaHud.java FabricaHudClara.java FabricaHudOscura.java InventarioClaro.java InventarioOscuro.java MainAbstractFactoryCS.java MarcadorClaro.java MarcadorOscuro.java
// Ejecutar: java codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
// Dominio: Counter-Strike — un HUD permite presentar el marcador y el inventario con temas claro u oscuro.
// Patrón: Abstract Factory; ejercicio independiente, no oficial.
// Glosario: HUD = indicadores superpuestos a la pantalla; inventario = lista de objetos virtuales; MP9 = subfusil representado como elemento del juego.
package codetrace.practica.counterstrike.abstractfactory;

/** Fábrica concreta: evita mezclar estilos claro y oscuro. */
public final class FabricaHudClara implements FabricaHud {
    @Override public EtiquetaMarcador crearMarcador() { return new MarcadorClaro(); }
    @Override public EtiquetaInventario crearInventario() { return new InventarioClaro(); }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EtiquetaInventario.java EtiquetaMarcador.java FabricaHud.java FabricaHudClara.java FabricaHudOscura.java InventarioClaro.java InventarioOscuro.java MainAbstractFactoryCS.java MarcadorClaro.java MarcadorOscuro.java
 *   java codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
 * Nota: FabricaHudClara.java se estudia junto al cliente MainAbstractFactoryCS.java.
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
