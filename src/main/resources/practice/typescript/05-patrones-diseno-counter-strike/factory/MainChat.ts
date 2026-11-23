// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — avisos del chat del equipo.
// Patrón: Simple Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { crearAviso } from "./Chat";
console.log(crearAviso("Ronda iniciada").mostrar());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-factory"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\factory\MainChat.ts
 *   node (Join-Path $out "MainChat.js")
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
