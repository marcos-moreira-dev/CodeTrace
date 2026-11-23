// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — ping = latencia de red mostrada en el HUD.
// Patrón: Adapter.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export class TelemetriaLegada {
    microsegundos(): number { return 42000; }
}
export interface VistaPing { mostrar(): string; }
export class AdaptadorPing implements VistaPing {
    constructor(private readonly origen: TelemetriaLegada) {}
    mostrar(): string {
        // El servidor antiguo informa microsegundos; la pantalla consume ms.
        return `Ping: ${Math.floor(this.origen.microsegundos() / 1000)} ms`;
    }
}

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
