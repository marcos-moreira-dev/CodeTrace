// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — avisos de rescate, minimapa e inventario.
// Patrón: Chain of Responsibility.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { crearCadena, type Aviso } from "./Cadena";
const cadena = crearCadena();
// Cuatro solicitudes entran por el mismo eslabón, incluida una no reconocida.
const avisos: Aviso[] = [
    { tipo: "rehen", detalle: "rehén virtual liberado" },
    { tipo: "rival", detalle: "rival marcado en el minimapa" },
    { tipo: "equipo", detalle: "MP9 registrado como objeto virtual" },
    { tipo: "otro", detalle: "evento del servidor no reconocido" },
];
for (const aviso of avisos) console.log(cadena.atender(aviso));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-chain-of-responsibility"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\chain-of-responsibility\MainCadena.ts
 *   node (Join-Path $out "MainCadena.js")
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
