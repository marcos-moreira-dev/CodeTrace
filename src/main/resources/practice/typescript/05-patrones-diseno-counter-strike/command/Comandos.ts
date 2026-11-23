// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — sumar y revertir una ronda CT.
// Patrón: Command.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export class Marcador {
    private puntosCt = 0;
    private puntosT = 0;
    get ct(): number { return this.puntosCt; }
    get t(): number { return this.puntosT; }
    sumarCt(): void { this.puntosCt++; }
    sumarT(): void { this.puntosT++; }
    restarCt(): void {
        if (this.puntosCt === 0) throw new Error("Marcador vacío");
        this.puntosCt--;
    }
}
export interface Comando { ejecutar(): void; deshacer(): void; }
export class SumarCt implements Comando {
    private ejecutado = false;
    constructor(private readonly receptor: Marcador) {}
    ejecutar(): void {
        if (this.ejecutado) throw new Error("Ya ejecutado");
        // Conservamos una referencia al marcador real de la partida.
        this.receptor.sumarCt(); this.ejecutado = true;
    }
    deshacer(): void {
        if (!this.ejecutado) throw new Error("No ejecutado");
        this.receptor.restarCt(); this.ejecutado = false;
    }
}

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
