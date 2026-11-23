// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — árbol de recursos de mapas.
// Patrón: Composite.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { ArchivoMapa, CarpetaMapa } from "./Recursos";
const raiz = new CarpetaMapa("maps", [
    new CarpetaMapa("oficiales", [new ArchivoMapa("de_dust2"), new ArchivoMapa("de_inferno")]),
    new ArchivoMapa("cs_office"),
]);
console.log(`Mapas registrados: ${raiz.contar()}`);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-composite"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\composite\MainRecursos.ts
 *   node (Join-Path $out "MainRecursos.js")
 * Salida esperada:
 *   Mapas registrados: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
