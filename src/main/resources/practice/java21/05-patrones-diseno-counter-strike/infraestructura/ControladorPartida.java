// CodeTrace | Referencia: Java 21 LTS / JDK 21
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

        MapaPartida mapa = new MapaPartida(
                "cs_office", "Oficinas", "Salas interiores", "Zona de extracción", 115);
        // List.of fija el roster: estos personajes ficticios no se crean
        // ni se eliminan durante las tres rondas de la demostración.
        Jugador guiaCt = new Jugador("Astra", Bando.CT, 1600);
        Equipo ct = new Equipo(Bando.CT, List.of(
                guiaCt, new Jugador("Nilo", Bando.CT, 1600)));
        Equipo t = new Equipo(Bando.T, List.of(
                new Jugador("Rook", Bando.T, 1600),
                new Jugador("Vega", Bando.T, 1600)));

        // Dos rehenes con rasgos DISTINTOS: uno está más calmado y otro
        // necesita más apoyo. Son personajes virtuales del objetivo.
        Rehen elena = new Rehen("Elena", "Archivo de oficinas", 92, 38);
        Rehen omar = new Rehen("Omar", "Sala de juntas", 86, 24);
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

        // Esta lista representa las rondas por ejecutar: NO hay Scanner
        // ni decisiones de usuario. Random(semilla) genera la misma secuencia.
        for (int numero = 1; numero <= configuracion.rondas(); numero++) {
            partida.iniciarRonda(numero);
            vista.abrirRonda(partida);

            boolean rondaEco = numero % 2 != 0;
            // Cambiamos la estrategia según el número de ronda. Strategy
            // evita escribir las dos políticas dentro de GestorEconomia.
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
                // Las rondas 1 y 3 son de rescate con esta semilla:
                // avanzamos al siguiente rehén cada dos rondas para que
                // las fichas de Elena y Omar aparezcan ambas en la historia.
                Rehen elegido = partida.rehenes().get(
                        ((numero - 1) / 2) % partida.rehenes().size());
                elegido.tranquilizar(12);
                elegido.iniciarEscolta(guiaCt);

                // Dos incrementos de progreso narran el recorrido hacia la
                // zona segura; al completar 100, el estado pasa a RESCATADO.
                elegido.avanzarHaciaSalida(40);
                elegido.avanzarHaciaSalida(60);
                partida.avanzarReloj(50 + numero * 5);

                vista.fichaRehen(elegido);
                vista.evento("CADENA", primero.atender(new AvisoRonda(
                        TipoAviso.REHEN, elegido.nombre() + " llegó a "
                                + partida.mapa().zonaSegura())));

                // DECORATOR: agrega etiquetas legibles a un mensaje simple,
                // sin modificar ni el aviso base ni su contenido original.
                vista.evento("NOTIFICACIÓN", new AvisoDeRonda(
                        new AvisoUrgente(new AvisoBase(
                                "Objetivo de rescate completado")), numero).texto());
                partida.registrarVictoria(Bando.CT,
                        "rescate de " + elegido.nombre());
            } else {
                // Simulamos que el reloj de la ronda llegó a cero. La ficha
                // del rehén sigue RETENIDO: nadie inició una escolta.
                partida.avanzarReloj(partida.segundosRestantes());
                vista.evento("CADENA", primero.atender(new AvisoRonda(
                        TipoAviso.OTRO, "tiempo agotado sin rescate")));
                partida.registrarVictoria(Bando.T, "objetivo sin completar");
            }

            // Los créditos que se agregan son una REGLA DIDÁCTICA fija.
            // No afirmamos que sean las recompensas oficiales del juego.
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
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * Con Random(42), los sorteos 30, 63 y 48 determinan rescate, fracaso y rescate.
 * Salida esperada:
 *   [AZAR] tirada=30 | desenlace=rescate
 *   [AZAR] tirada=63 | desenlace=sin rescate
 *   [AZAR] tirada=48 | desenlace=rescate
 *   Resultado: Gana CT
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
