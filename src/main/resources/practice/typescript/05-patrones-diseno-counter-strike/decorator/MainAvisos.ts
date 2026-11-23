// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — etiquetas de urgencia y número de ronda.
// Patrón: Decorator.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { urgente, deRonda } from "./Avisos";
const mensaje = deRonda(urgente(() => "Rehén virtual rescatado"), 12);
console.log(mensaje());

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
