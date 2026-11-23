// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — reglas de puntuación ficticias de torneos.
// Patrón: Strategy.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export type Regla = (victorias: number) => number;
export const casual: Regla = v => v * 2;
export const competitiva: Regla = v => v * 3;
export function puntos(victorias: number, regla: Regla): number {
    return regla(victorias);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-strategy-puntuacion"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\strategy-puntuacion\MainReglas.ts
 *   node (Join-Path $out "MainReglas.js")
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
