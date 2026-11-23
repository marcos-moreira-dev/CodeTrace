// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — reporte de partida en texto o CSV.
// Patrón: Template Method.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export abstract class Reporte {
    generar(mapa: string, puntos: number): string {
        // La plantilla fija el orden de los pasos del reporte.
        return this.encabezado(mapa) + this.detalle(puntos);
    }
    protected abstract encabezado(mapa: string): string;
    protected abstract detalle(puntos: number): string;
}
export class ReporteTexto extends Reporte {
    protected encabezado(mapa: string): string { return `Partida ${mapa}: `; }
    protected detalle(puntos: number): string { return `CT=${puntos}`; }
}
export class ReporteCsv extends Reporte {
    protected encabezado(mapa: string): string { return `mapa,rondasCt;${mapa},`; }
    protected detalle(puntos: number): string { return String(puntos); }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-template-method"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\template-method\MainReportes.ts
 *   node (Join-Path $out "MainReportes.js")
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
