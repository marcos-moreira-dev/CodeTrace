// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — partida automática de rescate de NPC.
// Glosario: CT/T = bandos; MP9 = objeto virtual; eco = ahorro de créditos.

import { Marcador, SumarCt } from "../command/Comandos";
import type { Bando } from "../observer/MarcadorObservable";
import type { Jugador } from "./Jugador";
import type { Rehen } from "./Rehen";
export class Partida {
    readonly marcador = new Marcador();
    private registros: string[] = [];
    reloj = 115;
    constructor(readonly mapa: string, readonly ct: readonly Jugador[],
                readonly t: readonly Jugador[], readonly rehenes: readonly Rehen[]) {}
    get historial(): readonly string[] { return [...this.registros]; }
    iniciarRonda(): void {
        this.reloj = 115;
        // Las fichas de jugadores y NPC se reinician; créditos y puntos sobreviven.
        for (const jugador of [...this.ct, ...this.t]) jugador.prepararRonda();
        for (const rehen of this.rehenes) rehen.reiniciar();
    }
    registrar(ganador: Bando, motivo: string): void {
        if (ganador === "CT") new SumarCt(this.marcador).ejecutar();
        else this.marcador.sumarT();
        // Guardamos una descripción, no una referencia al NPC que se reiniciará.
        this.registros.push(`${ganador}: ${motivo}`);
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
