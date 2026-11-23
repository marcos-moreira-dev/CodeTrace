// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — partida automática de rescate de NPC.
// Glosario: CT/T = bandos; MP9 = objeto virtual; eco = ahorro de créditos.

import type { Bando } from "../observer/MarcadorObservable";
export interface ObjetoVirtual {
    readonly nombre: string;
    readonly categoria: string;
    readonly precio: number;
}
export class Jugador {
    private saldo = 1600;
    private objetos: ObjetoVirtual[] = [];
    vida = 100;
    armadura = 35;
    constructor(readonly alias: string, readonly bando: Bando) {}
    get creditos(): number { return this.saldo; }
    get inventario(): readonly ObjetoVirtual[] { return [...this.objetos]; }
    prepararRonda(): void { this.vida = 100; this.armadura = 35; }
    recompensar(): void { this.saldo += 300; }
    comprar(item: ObjetoVirtual): boolean {
        // MP9 es una ficha virtual. Si faltan créditos, no cambia el inventario.
        if (!Number.isInteger(item.precio) || item.precio < 0 || this.saldo < item.precio) return false;
        this.saldo -= item.precio;
        this.objetos.push(Object.freeze({ ...item }));
        return true;
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
