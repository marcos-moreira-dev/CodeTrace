// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — familias de etiquetas de marcador e inventario virtual.
// Patrón: Abstract Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export interface EtiquetaMarcador { mostrar(puntos: number): string; }
export interface EtiquetaInventario { mostrar(objeto: string): string; }
export interface FamiliaHud {
    marcador(): EtiquetaMarcador;
    inventario(): EtiquetaInventario;
}
export class FamiliaTema implements FamiliaHud {
    constructor(private readonly tema: "CLARO" | "OSCURO") {}
    marcador(): EtiquetaMarcador {
        return { mostrar: puntos => `[${this.tema}] CT=${puntos}` };
    }
    inventario(): EtiquetaInventario {
        // Ambos productos comparten el tema para mantener coherente el HUD.
        return { mostrar: objeto => `[${this.tema}] equipo=${objeto}` };
    }
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
