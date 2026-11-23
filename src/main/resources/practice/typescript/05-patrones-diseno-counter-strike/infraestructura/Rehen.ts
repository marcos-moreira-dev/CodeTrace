// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — partida automática de rescate de NPC.
// Glosario: CT/T = bandos; MP9 = objeto virtual; eco = ahorro de créditos.

import type { Jugador } from "./Jugador";
export type EstadoRehen = "RETENIDO" | "ESCOLTADO" | "RESCATADO";
/** NPC del modo rescate; la ubicación es su lugar de aparición, no una coordenada. */
export class Rehen {
    private _estado: EstadoRehen = "RETENIDO";
    private _calma: number;
    private _progreso = 0;
    private _escolta = "";
    constructor(readonly nombre: string, readonly ubicacion: string,
                readonly integridad: number, private readonly calmaInicial: number) {
        if (![integridad, calmaInicial].every(v => Number.isInteger(v) && v >= 0 && v <= 100))
            throw new Error("Ficha de NPC inválida");
        this._calma = calmaInicial;
    }
    get estado(): EstadoRehen { return this._estado; }
    get calma(): number { return this._calma; }
    get progreso(): number { return this._progreso; }
    get escolta(): string { return this._escolta; }
    reiniciar(): void {
        // El objetivo vuelve a estar pendiente; no se crea otro objeto en memoria.
        this._estado = "RETENIDO"; this._calma = this.calmaInicial;
        this._progreso = 0; this._escolta = "";
    }
    rescatar(jugador: Jugador): void {
        if (jugador.bando !== "CT" || this._estado !== "RETENIDO") throw new Error("Escolta inválida");
        this._escolta = jugador.alias;
        this._calma = Math.min(100, this._calma + 12);
        this._estado = "ESCOLTADO";
        // 100% representa la llegada abstracta a la zona de extracción.
        this._progreso = 100; this._estado = "RESCATADO";
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
