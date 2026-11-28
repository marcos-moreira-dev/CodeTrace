// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . EtiquetaInventario.java EtiquetaMarcador.java FabricaHud.java FabricaHudClara.java FabricaHudOscura.java InventarioClaro.java InventarioOscuro.java MainAbstractFactoryCS.java MarcadorClaro.java MarcadorOscuro.java
// Ejecutar: java codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
// Dominio: Counter-Strike — un HUD permite presentar el marcador y el inventario con temas claro u oscuro.
// Patrón: Abstract Factory; ejercicio independiente, no oficial.
// Glosario: HUD = indicadores superpuestos a la pantalla; inventario = lista de objetos virtuales; MP9 = subfusil representado como elemento del juego.
package codetrace.practica.counterstrike.abstractfactory;

/** Producto abstracto 2: cómo mostrar un objeto del inventario virtual. */
public interface EtiquetaInventario { String dibujar(String item); }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EtiquetaInventario.java EtiquetaMarcador.java FabricaHud.java FabricaHudClara.java FabricaHudOscura.java InventarioClaro.java InventarioOscuro.java MainAbstractFactoryCS.java MarcadorClaro.java MarcadorOscuro.java
 *   java codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
 * Nota: EtiquetaInventario.java se estudia junto al cliente MainAbstractFactoryCS.java.
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
