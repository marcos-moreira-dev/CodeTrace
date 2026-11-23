// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — familias de etiquetas de marcador e inventario virtual.
// Patrón: Abstract Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { FamiliaTema } from "./FamiliasHud";
// Los literales preservan los dos temas admitidos, no cualquier string.
for (const [nombre, tema] of [["Claro", "CLARO"], ["Oscuro", "OSCURO"]] as const) {
    const familia = new FamiliaTema(tema);
    console.log(`${nombre}: ${familia.marcador().mostrar(5)} | ${familia.inventario().mostrar("MP9")}`);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-abstract-factory"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\abstract-factory\MainFamiliasHud.ts
 *   node (Join-Path $out "MainFamiliasHud.js")
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
