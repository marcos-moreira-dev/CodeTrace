// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — etiquetas de urgencia y número de ronda.
// Patrón: Decorator.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export type AvisoEquipo = () => string;
export function urgente(aviso: AvisoEquipo): AvisoEquipo {
    // La función envuelta permanece intacta; añadimos una etiqueta al resultado.
    return () => "[URGENTE] " + aviso();
}
export function deRonda(aviso: AvisoEquipo, ronda: number): AvisoEquipo {
    return () => `[RONDA ${ronda}] ` + aviso();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-decorator"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\decorator\MainAvisos.ts
 *   node (Join-Path $out "MainAvisos.js")
 * Salida esperada:
 *   [RONDA 12] [URGENTE] Rehén virtual rescatado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
