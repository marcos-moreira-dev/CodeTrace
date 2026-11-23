// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — ping = latencia de red mostrada en el HUD.
// Patrón: Adapter.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { AdaptadorPing, TelemetriaLegada, type VistaPing } from "./Ping";
const hud: VistaPing = new AdaptadorPing(new TelemetriaLegada());
console.log(hud.mostrar());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-adapter"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\adapter\MainPing.ts
 *   node (Join-Path $out "MainPing.js")
 * Salida esperada:
 *   Ping: 42 ms
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
