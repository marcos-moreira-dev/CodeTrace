// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — eco = ahorrar créditos del juego.
// Patrón: Strategy.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export type Politica = (creditos: number) => string;
export const eco: Politica = creditos => `Eco: guardar ${creditos} créditos`;
export const equipo: Politica = creditos => `Equipo: asignar ${creditos} créditos virtuales`;
export class Economia {
    constructor(public politica: Politica) {}
    plan(creditos: number): string {
        // La política no posee el dinero; consulta el saldo del jugador.
        return this.politica(creditos);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-strategy"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\strategy\MainEconomia.ts
 *   node (Join-Path $out "MainEconomia.js")
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
