// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — eco = ahorrar créditos del juego.
// Patrón: Strategy.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { Economia, eco, equipo } from "./Economia";
const gestor = new Economia(eco);
console.log(gestor.plan(1500));
gestor.politica = equipo;
console.log(gestor.plan(1500));

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
