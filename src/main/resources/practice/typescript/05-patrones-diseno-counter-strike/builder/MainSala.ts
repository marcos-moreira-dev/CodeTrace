// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — configuración de una sala de rescate.
// Patrón: Builder.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { Configurador } from "./Sala";
// Encadenamos las elecciones del anfitrión y validamos al construir.
const s = new Configurador().mapa("cs_office").rondas(12).rescate(true).construir();
console.log(`Sala: ${s.mapa}, rondas=${s.rondas}, rehenes=${s.rehenes}`);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-builder"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\builder\MainSala.ts
 *   node (Join-Path $out "MainSala.js")
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
