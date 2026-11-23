// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — reportes de puntos CT.
// Patrón: Factory Method.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { CreadorTexto, CreadorCsv } from "./Exportadores";
console.log("Texto: " + new CreadorTexto().publicar(7));
console.log("CSV: " + new CreadorCsv().publicar(7));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-factory-method"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\factory-method\MainExportadores.ts
 *   node (Join-Path $out "MainExportadores.js")
 * Salida esperada:
 *   Texto: CT=7
 *   CSV: equipo,rondas;CT,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
