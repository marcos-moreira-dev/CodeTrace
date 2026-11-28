// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . EtiquetaInventario.java EtiquetaMarcador.java FabricaHud.java FabricaHudClara.java FabricaHudOscura.java InventarioClaro.java InventarioOscuro.java MainAbstractFactoryCS.java MarcadorClaro.java MarcadorOscuro.java
// Ejecutar: java codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
// Dominio: Counter-Strike — un HUD permite presentar el marcador y el inventario con temas claro u oscuro.
// Patrón: Abstract Factory; ejercicio independiente, no oficial.
// Glosario: HUD = indicadores superpuestos a la pantalla; inventario = lista de objetos virtuales; MP9 = subfusil representado como elemento del juego.
package codetrace.practica.counterstrike.abstractfactory;

import java.util.List;

/** Cliente: trabaja solo con contratos, nunca con productos concretos. */
public final class MainAbstractFactoryCS {
    public static void main(String[] args) {
        // List.of reúne las dos fábricas para repetir una única demostración.
        List<FabricaHud> temas = List.of(new FabricaHudClara(), new FabricaHudOscura());
        // Este ciclo prueba que el cliente no cambia cuando cambia la familia.
        for (int i = 0; i < temas.size(); i++) {
            FabricaHud familia = temas.get(i);
            EtiquetaMarcador marcador = familia.crearMarcador();
            EtiquetaInventario inventario = familia.crearInventario();
            String nombre = i == 0 ? "Claro" : "Oscuro";
            // MP9 es solo el nombre de un objeto virtual mostrado en la interfaz.
            System.out.println(nombre + ": " + marcador.dibujar(5) + " | "
                    + inventario.dibujar("MP9"));
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EtiquetaInventario.java EtiquetaMarcador.java FabricaHud.java FabricaHudClara.java FabricaHudOscura.java InventarioClaro.java InventarioOscuro.java MainAbstractFactoryCS.java MarcadorClaro.java MarcadorOscuro.java
 *   java codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS
 * Nota: MainAbstractFactoryCS.java se estudia junto al cliente MainAbstractFactoryCS.java.
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
