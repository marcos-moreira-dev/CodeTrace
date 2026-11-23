// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — sumar y revertir una ronda CT.
// Patrón: Command.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { Marcador, SumarCt, type Comando } from "./Comandos";
const marcador = new Marcador();
const comando: Comando = new SumarCt(marcador);
comando.ejecutar();
console.log(`Tras ejecutar: CT=${marcador.ct}`);
comando.deshacer();
console.log(`Tras deshacer: CT=${marcador.ct}`);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-command"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\command\MainComandos.ts
 *   node (Join-Path $out "MainComandos.js")
 * Salida esperada:
 *   Tras ejecutar: CT=1
 *   Tras deshacer: CT=0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
