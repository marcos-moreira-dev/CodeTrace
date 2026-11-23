// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — actualizaciones del HUD mediante callbacks tipados.
// Patrón: Observer.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

import { MarcadorObservable } from "./MarcadorObservable";
const marcador = new MarcadorObservable();
const cancelar = marcador.suscribir(({ equipo, puntos }) => console.log(`HUD: ${equipo}=${puntos}`));
marcador.anotar("CT", 2);
marcador.anotar("T", 1);
cancelar();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-observer"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\observer\MainMarcadorObservable.ts
 *   node (Join-Path $out "MainMarcadorObservable.js")
 * Salida esperada:
 *   HUD: CT=2
 *   HUD: T=1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
