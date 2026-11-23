// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — partida automática de rescate de NPC.
// Glosario: CT/T = bandos; MP9 = objeto virtual; eco = ahorro de créditos.

import { AdaptadorPing, TelemetriaLegada } from "../adapter/Ping";
import { Configurador } from "../builder/Sala";
import { crearCadena } from "../chain-of-responsibility/Cadena";
import { ArchivoMapa, CarpetaMapa } from "../composite/Recursos";
import { CreadorTexto } from "../factory-method/Exportadores";
import { MarcadorObservable } from "../observer/MarcadorObservable";
import { Economia, eco, equipo } from "../strategy/Economia";
import { Jugador } from "./Jugador";
import { Rehen } from "./Rehen";
import { Partida } from "./Partida";
import type { VistaPartida } from "./VistaConsola";
export class ControladorPartida {
    constructor(private readonly vista: VistaPartida) {}
    simular(): Partida {
        const sala = new Configurador().mapa("cs_office").rondas(3).rescate(true).construir();
        const astra = new Jugador("Astra", "CT");
        // Las listas guardan los mismos objetos que usa la tienda y el rescate.
        const p = new Partida(sala.mapa, [astra, new Jugador("Nilo", "CT")],
            [new Jugador("Rook", "T"), new Jugador("Vega", "T")],
            [new Rehen("Elena", "Archivo", 92, 38), new Rehen("Omar", "Sala de juntas", 86, 24)]);
        const recursos = new CarpetaMapa("maps", [
            new CarpetaMapa("oficiales", [new ArchivoMapa("cs_office")]), new ArchivoMapa("de_dust2"),
        ]);
        this.vista.presentar(p, sala.rondas, new AdaptadorPing(new TelemetriaLegada()).mostrar(), recursos.contar());
        const cadena = crearCadena();
        const observable = new MarcadorObservable();
        // La función flecha conserva this; el callback recibe un evento tipado.
        const cancelar = observable.suscribir(e => this.vista.marcador(e));
        const economia = new Economia(eco);
        // Guion equivalente a Java con semilla 42, para comparar lenguajes.
        const desenlaces = [true, false, true] as const;
        let indiceRehen = 0;
        for (let numero = 1; numero <= sala.rondas; numero++) {
            p.iniciarRonda();
            this.vista.evento(`--- RONDA ${numero} ---`);
            economia.politica = numero % 2 ? eco : equipo;
            this.vista.evento(economia.plan(astra.creditos));
            if (numero % 2 === 0 && astra.comprar({ nombre: "MP9", categoria: "subfusil", precio: 950 }))
                this.vista.evento(cadena.atender({ tipo: "equipo", detalle: "MP9 registrado como objeto virtual" }));
            if (desenlaces[numero - 1]) {
                // Seleccionamos el NPC ya registrado, no una copia.
                // Al llegar a extracción cambia su ficha y el historial recibe el resultado.
                const rehen = p.rehenes[indiceRehen++ % p.rehenes.length];
                rehen.rescatar(astra);
                p.reloj = 60;
                this.vista.evento(cadena.atender({ tipo: "rehen", detalle: `${rehen.nombre} llegó a la zona de extracción` }));
                this.vista.ficha(rehen);
                p.registrar("CT", `rescate de ${rehen.nombre}`);
            } else {
                p.reloj = 0;
                this.vista.evento("Tiempo agotado sin rescate");
                p.registrar("T", "objetivo sin completar");
            }
            astra.recompensar();
            observable.anotar("CT", p.marcador.ct);
            observable.anotar("T", p.marcador.t);
        }
        // Como al destruir una vista web, dejamos de escuchar los eventos.
        cancelar();
        this.vista.finalizar(p, new CreadorTexto().publicar(p.marcador.ct));
        return p;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-integrador"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\MainCounterStrike.ts
 *   node (Join-Path $out "MainCounterStrike.js")
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   Mapa: cs_office | Rondas: 3 | Ping: 42 ms
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 *   Mapas registrados: 2
 *   --- RONDA 1 ---
 *   Eco: guardar 1600 créditos
 *   Rescate: Elena llegó a la zona de extracción
 *   Rehén Elena: RESCATADO | escolta=Astra | calma=50 | integridad=92
 *   Marcador: CT 1 - 0 T
 *   --- RONDA 2 ---
 *   Equipo: asignar 1900 créditos virtuales
 *   Inventario: MP9 registrado como objeto virtual
 *   Tiempo agotado sin rescate
 *   Marcador: CT 1 - 1 T
 *   --- RONDA 3 ---
 *   Eco: guardar 1250 créditos
 *   Rescate: Omar llegó a la zona de extracción
 *   Rehén Omar: RESCATADO | escolta=Astra | calma=36 | integridad=86
 *   Marcador: CT 2 - 1 T
 *   === FIN DE LA PARTIDA ===
 *   Resultado: Gana CT
 *   Créditos Astra: 1550
 *   Reporte: CT=2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
