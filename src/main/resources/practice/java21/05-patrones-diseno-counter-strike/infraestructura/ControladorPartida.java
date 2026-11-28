// Compilar (desde la raíz de 05-patrones-diseno-counter-strike): javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike; simulación automática del mapa cs_office.
// Glosario: CT/T = bandos; MP9 = objeto virtual; HUD = información de pantalla.
// Arquitectura: CONTROLADOR MVC. Coordina varios patrones de diseño existentes.
package codetrace.practica.counterstrike.infraestructura;

import codetrace.practica.counterstrike.abstractfactory.FabricaHud;
import codetrace.practica.counterstrike.abstractfactory.FabricaHudClara;
import codetrace.practica.counterstrike.abstractfactory.FabricaHudOscura;
import codetrace.practica.counterstrike.adapter.AdaptadorPing;
import codetrace.practica.counterstrike.adapter.TelemetriaLegada;
import codetrace.practica.counterstrike.builder.ConfiguracionSala;
import codetrace.practica.counterstrike.builder.ConfiguradorSala;
import codetrace.practica.counterstrike.cadena.AvisoRonda;
import codetrace.practica.counterstrike.cadena.ManejadorAviso;
import codetrace.practica.counterstrike.cadena.ManejadorInventario;
import codetrace.practica.counterstrike.cadena.ManejadorReconocimiento;
import codetrace.practica.counterstrike.cadena.ManejadorRescate;
import codetrace.practica.counterstrike.cadena.TipoAviso;
import codetrace.practica.counterstrike.composite.ArchivoMapa;
import codetrace.practica.counterstrike.composite.CarpetaMapa;
import codetrace.practica.counterstrike.decorator.AvisoBase;
import codetrace.practica.counterstrike.decorator.AvisoDeRonda;
import codetrace.practica.counterstrike.decorator.AvisoUrgente;
import codetrace.practica.counterstrike.factorymethod.CreadorCsv;
import codetrace.practica.counterstrike.factorymethod.CreadorTexto;
import codetrace.practica.counterstrike.observer.MarcadorRonda;
import codetrace.practica.counterstrike.strategy.GestorEconomia;
import codetrace.practica.counterstrike.strategy.PoliticaEco;
import codetrace.practica.counterstrike.strategy.PoliticaEquipo;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * CONTROLADOR de una partida ficticia de Counter-Strike, relatada por consola.
 *
 * Cada patrón explica una responsabilidad concreta, no participa por adorno:
 * Builder prepara el modo, Adapter traduce el ping, Composite cuenta mapas,
 * Strategy decide cómo PRESENTAR la política económica, Chain of
 * Responsibility delega eventos, Decorator adorna avisos, Command registra
 * victorias CT, Observer actualiza espectadores y Abstract Factory genera HUD.
 *
 * Random solo selecciona un desenlace ficticio de cada ronda. El programa
 * no espera teclas, no reproduce el motor del juego y no simula balística.
 */
public final class ControladorPartida {
    private static final int UMBRAL_RESCATE = 60; // Probabilidad didáctica, no tasa real.
    private final VistaConsola vista;
    private final long semilla;
    private final Random aleatorio;

    public ControladorPartida(VistaConsola vista, long semilla) {
        this.vista = Objects.requireNonNull(vista, "vista");
        this.semilla = semilla;
        this.aleatorio = new Random(semilla);
    }

    public EstadoPartida simular() {
        // BUILDER: cada llamada fija una opción y construir() valida el conjunto.
        // cs_office es un mapa de rescate de Counter-Strike; no cargamos su arte.
        ConfiguracionSala configuracion = new ConfiguradorSala()
                .mapa("cs_office").rondas(3).rescateRehenes(true).construir();

        // La sesión guarda en memoria el mapa seleccionado, las etiquetas
        // del objetivo de rescate y el reloj inicial de cada ronda (115 s).
        // Son datos de nuestro escenario, no coordenadas del mapa real.
        MapaPartida mapa = new MapaPartida(
                "cs_office", "Oficinas", "Salas interiores", "Zona de extracción", 115);
        // Creamos cuatro personajes virtuales con 1600 créditos cada uno.
        // List.of fija las alineaciones CT y T: durante la partida cambian
        // sus atributos, pero no aparecen ni desaparecen nuevos jugadores.
        Jugador guiaCt = new Jugador("Astra", Bando.CT, 1600);
        Equipo ct = new Equipo(Bando.CT, List.of(
                guiaCt, new Jugador("Nilo", Bando.CT, 1600)));
        Equipo t = new Equipo(Bando.T, List.of(
                new Jugador("Rook", Bando.T, 1600),
                new Jugador("Vega", Bando.T, 1600)));

        // En el modo rescate los objetivos son NPC, no jugadores de CT/T.
        // Creamos sus fichas en memoria: Elena empieza en el archivo y Omar
        // en la sala de juntas. Los dos poseen integridad y calma distintas.
        Rehen elena = new Rehen("Elena", "Archivo de oficinas", 92, 38);
        Rehen omar = new Rehen("Omar", "Sala de juntas", 86, 24);

        // Registramos ambas referencias en la partida. El controlador y la
        // vista consultarán esos mismos objetos, no copias de los rehenes.
        // List.of fija los dos objetivos, pero sus estados sí pueden cambiar.
        EstadoPartida partida = new EstadoPartida(
                configuracion, mapa, ct, t, List.of(elena, omar));
        vista.presentar(partida, semilla);

        // ADAPTER: el servidor legado informa microsegundos; el HUD moderno
        // solicita milisegundos. No cambiamos la clase TelemetriaLegada.
        vista.evento("RED", new AdaptadorPing(
                new TelemetriaLegada()).mostrarPing());

        // COMPOSITE: una carpeta contiene tanto archivos como otra carpeta.
        // contar() recorre los recursos, sin que el cliente distinga su tipo.
        CarpetaMapa recursos = new CarpetaMapa("recursos");
        CarpetaMapa oficiales = new CarpetaMapa("oficiales");
        oficiales.agregar(new ArchivoMapa("cs_office"));
        recursos.agregar(oficiales);
        recursos.agregar(new ArchivoMapa("de_dust2"));
        vista.evento("RECURSOS", "Mapas de muestra: " + recursos.contar());

        // CHAIN OF RESPONSIBILITY: cada especialista entiende un tipo de
        // aviso. Si no le compete, lo reenvía; el cliente solo ve 'primero'.
        ManejadorAviso primero = new ManejadorRescate();
        primero.enlazar(new ManejadorReconocimiento())
               .enlazar(new ManejadorInventario());

        // OBSERVER: el marcador no conoce las vistas concretas. Registramos
        // una función que recibe el equipo y su puntuación tras cada ronda.
        MarcadorRonda marcadorObservable = new MarcadorRonda();
        marcadorObservable.suscribir((equipo, puntos) ->
                vista.evento("OBSERVER/HUD", equipo + "=" + puntos));

        // STRATEGY: la política 'eco' informa de ahorro y la de equipo
        // informa de asignación. El saldo REAL vive en Jugador.comprar().
        GestorEconomia economia = new GestorEconomia(new PoliticaEco());
        ObjetoVirtual mp9 = new ObjetoVirtual("MP9", "subfusil", 950, 30);

        // Cada iteración representa una ronda completa de Counter-Strike:
        // reinicio de objetivos, economía, desenlace, marcador y HUD.
        // No existe una lista de rondas ni esperamos decisiones del usuario;
        // el contador del for y la configuración determinan cuántas se juegan.
        for (int numero = 1; numero <= configuracion.rondas(); numero++) {
            partida.iniciarRonda(numero);
            vista.abrirRonda(partida);

            // El módulo (%) separa las rondas impares de las pares. En esta
            // simulación la fase de ahorro se alterna con una fase de compra.
            boolean rondaEco = numero % 2 != 0;
            // Strategy intercambia la política económica que se anuncia;
            // el saldo y el inventario continúan siendo datos de Astra.
            economia.cambiarPolitica(rondaEco
                    ? new PoliticaEco() : new PoliticaEquipo());
            vista.evento("ECONOMÍA", economia.plan(guiaCt.creditos()));

            if (!rondaEco) {
                // La ronda 2 permite comprar un objeto virtual. Comprar
                // resta créditos SOLO si hay saldo; una ronda eco lo conserva.
                if (guiaCt.comprar(mp9)) {
                    vista.evento("TIENDA", guiaCt.alias() + " añadió "
                            + mp9.nombre() + " | créditos=" + guiaCt.creditos()
                            + " | capacidad ilustrativa=" + mp9.capacidadVirtual());
                    vista.evento("CADENA", primero.atender(new AvisoRonda(
                            TipoAviso.EQUIPAMIENTO, "MP9 en inventario virtual")));
                } else {
                    vista.evento("TIENDA", "Compra omitida por saldo insuficiente");
                }
            }

            // El único sorteo por ronda es este número entre 0 y 99.
            // Menos de 60 significa rescate; el resto, tiempo agotado.
            // Es una regla de esta simulación, NO una regla de Counter-Strike.
            int tirada = aleatorio.nextInt(100);
            boolean rescate = tirada < UMBRAL_RESCATE;
            vista.evento("AZAR", "tirada=" + tirada
                    + " | desenlace=" + (rescate ? "rescate" : "sin rescate"));

            if (rescate) {
                // Con la semilla 42, la primera y la tercera ronda terminan
                // con rescate. Elegimos el NPC registrado en la lista:
                // el índice es 0 en ronda 1 (Elena) y 1 en ronda 3 (Omar).
                // 'elegido' referencia la ficha existente en EstadoPartida;
                // no crea otro NPC ni duplica sus atributos en memoria.
                Rehen elegido = partida.rehenes().get(
                        ((numero - 1) / 2) % partida.rehenes().size());

                // La historia representa el encuentro de Astra con el NPC.
                // Aumentamos la calma y guardamos su alias como escolta;
                // iniciarEscolta comprueba que sea CT y que siga RETENIDO.
                elegido.tranquilizar(12);
                elegido.iniciarEscolta(guiaCt);

                // El rescate se representa con dos cambios del progreso
                // abstracto del NPC: 40 % y 100 %. Al completarlo, la ficha
                // en memoria queda RESCATADA. No calculamos una ruta real.
                elegido.avanzarHaciaSalida(40);
                elegido.avanzarHaciaSalida(60);

                // Descontamos el tiempo ficticio de la ronda. No usamos
                // sleep(): el reloj es un dato, no una espera del programa.
                partida.avanzarReloj(50 + numero * 5);

                // Mostramos la ficha ya actualizada y anunciamos la llegada
                // a la zona de extracción. La ubicación de la ficha sigue
                // siendo su lugar de aparición, no una posición en tiempo real.
                vista.fichaRehen(elegido);
                vista.evento("CADENA", primero.atender(new AvisoRonda(
                        TipoAviso.REHEN, elegido.nombre() + " llegó a "
                                + partida.mapa().zonaSegura())));

                // DECORATOR: agrega etiquetas legibles a un mensaje simple,
                // sin modificar ni el aviso base ni su contenido original.
                vista.evento("NOTIFICACIÓN", new AvisoDeRonda(
                        new AvisoUrgente(new AvisoBase(
                                "Objetivo de rescate completado")), numero).texto());
                // El objetivo completado da un punto a CT y conserva el
                // nombre del NPC rescatado en el historial de esta ronda.
                // Ese registro sobrevivirá al reinicio de los rehenes.
                partida.registrarVictoria(Bando.CT,
                        "rescate de " + elegido.nombre());
            } else {
                // Vence el reloj sin que ningún NPC haya cambiado a RESCATADO.
                // Sus fichas siguen RETENIDO y T recibe el punto de la ronda.
                // El aviso OTRO muestra también el caso sin handler compatible.
                partida.avanzarReloj(partida.segundosRestantes());
                vista.evento("CADENA", primero.atender(new AvisoRonda(
                        TipoAviso.OTRO, "tiempo agotado sin rescate")));
                partida.registrarVictoria(Bando.T, "objetivo sin completar");
            }

            // Astra cobra 300 créditos ficticios al finalizar cada ronda.
            // La recompensa se suma al saldo restante después de las compras;
            // por eso la próxima ronda comienza con una economía diferente.
            // Es una regla de nuestro ejemplo, no una recompensa oficial.
            guiaCt.recibirCreditos(300);
            vista.evento("ESTADO", "tiempo restante="
                    + partida.segundosRestantes() + " s | créditos de "
                    + guiaCt.alias() + "=" + guiaCt.creditos());

            // Observer avisa ambas puntuaciones a sus suscriptores.
            // Contrasta con Chain: aquí todos los observadores reciben datos.
            marcadorObservable.anotar("CT", partida.puntosCt());
            marcadorObservable.anotar("T", partida.puntosT());

            // ABSTRACT FACTORY: cambiar el tema del HUD cambia una familia
            // COMPLETA de etiquetas, no solo una clase aislada.
            FabricaHud hud = numero % 2 == 1
                    ? new FabricaHudClara() : new FabricaHudOscura();
            // El HUD consulta el inventario REAL del jugador: en la ronda
            // eco inicial todavía NO compró MP9. Mostrarlo antes sería una
            // contradicción entre modelo y presentación.
            String itemHud = guiaCt.inventario().isEmpty()
                    ? "sin compra" : guiaCt.inventario().getFirst().nombre();
            vista.evento("HUD", hud.crearMarcador().dibujar(partida.puntosCt())
                    + " | " + hud.crearInventario().dibujar(itemHud));
            vista.marcador(partida);
        }

        vista.cerrarPartida(partida);

        // FACTORY METHOD: dos creadores aplican el mismo flujo 'publicar',
        // pero seleccionan exportadores diferentes. El resultado es texto,
        // no se generan archivos CSV ni se accede al disco.
        vista.evento("REPORTE TEXTO",
                new CreadorTexto().publicar(partida.puntosCt()));
        vista.evento("REPORTE CSV",
                new CreadorCsv().publicar(partida.puntosCt()));
        return partida;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   [AZAR] tirada=30 | desenlace=rescate
 *   [AZAR] tirada=63 | desenlace=sin rescate
 *   [AZAR] tirada=48 | desenlace=rescate
 *   Resultado: Gana CT
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
