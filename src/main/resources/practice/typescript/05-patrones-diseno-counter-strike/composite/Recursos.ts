// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — árbol de recursos de mapas.
// Patrón: Composite.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export interface Recurso { contar(): number; }
export class ArchivoMapa implements Recurso {
    constructor(readonly nombre: string) {}
    contar(): number { return 1; }
}
export class CarpetaMapa implements Recurso {
    constructor(readonly nombre: string, private readonly hijos: readonly Recurso[]) {}
    contar(): number {
        // Cada hijo puede ser un mapa o una carpeta; ambos saben contar().
        return this.hijos.reduce((total, hijo) => total + hijo.contar(), 0);
    }
}

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
