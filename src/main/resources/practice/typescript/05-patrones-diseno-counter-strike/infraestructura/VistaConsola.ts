// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — partida automática de rescate de NPC.
// Glosario: CT/T = bandos; MP9 = objeto virtual; eco = ahorro de créditos.

import type { EventoMarcador } from "../observer/MarcadorObservable";
import type { Partida } from "./Partida";
import type { Rehen } from "./Rehen";
/** Contrato de presentación: podría implementarse con componentes web. */
export interface VistaPartida {
    presentar(partida: Partida, rondas: number, ping: string, recursos: number): void;
    evento(texto: string): void;
    ficha(rehen: Rehen): void;
    marcador(evento: EventoMarcador): void;
    finalizar(partida: Partida, reporte: string): void;
}
export class VistaConsola implements VistaPartida {
    private ct = 0;
    presentar(p: Partida, rondas: number, ping: string, recursos: number): void {
        console.log("=== COUNTER-STRIKE | PARTIDA DIDÁCTICA ===");
        console.log(`Mapa: ${p.mapa} | Rondas: ${rondas} | ${ping}`);
        // map() extrae alias de las fichas y join() los convierte en texto.
        console.log(`Equipos: CT (${p.ct.map(j => j.alias).join(", ")}) vs T (${p.t.map(j => j.alias).join(", ")})`);
        console.log(`Mapas registrados: ${recursos}`);
    }
    evento(texto: string): void { console.log(texto); }
    ficha(r: Rehen): void {
        console.log(`Rehén ${r.nombre}: ${r.estado} | escolta=${r.escolta} | calma=${r.calma} | integridad=${r.integridad}`);
    }
    marcador({ equipo, puntos }: EventoMarcador): void {
        // Observer publica CT y después T; con ambos valores dibujamos el marcador.
        if (equipo === "CT") this.ct = puntos;
        else console.log(`Marcador: CT ${this.ct} - ${puntos} T`);
    }
    finalizar(p: Partida, reporte: string): void {
        const { ct, t } = p.marcador;
        console.log("=== FIN DE LA PARTIDA ===");
        console.log(`Resultado: ${ct > t ? "Gana CT" : t > ct ? "Gana T" : "Empate"}`);
        console.log(`Créditos Astra: ${p.ct[0].creditos}`);
        console.log(`Reporte: ${reporte}`);
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
