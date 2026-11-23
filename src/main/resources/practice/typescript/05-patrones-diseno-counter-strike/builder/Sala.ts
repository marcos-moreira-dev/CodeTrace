// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — configuración de una sala de rescate.
// Patrón: Builder.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export interface Sala {
    readonly mapa: string;
    readonly rondas: number;
    readonly rehenes: boolean;
}
export class Configurador {
    private opciones = { mapa: "de_dust2", rondas: 6, rehenes: false };
    mapa(nombre: string): this { this.opciones.mapa = nombre; return this; }
    rondas(cantidad: number): this { this.opciones.rondas = cantidad; return this; }
    rescate(valor: boolean): this { this.opciones.rehenes = valor; return this; }
    construir(): Sala {
        if (!this.opciones.mapa.trim() || !Number.isInteger(this.opciones.rondas) || this.opciones.rondas <= 0)
            throw new Error("Sala inválida");
        // Copiamos la configuración de la sala; el builder puede reutilizarse.
        return Object.freeze({ ...this.opciones });
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-ts-builder"
 *   tsc --strict --target ES2022 --module commonjs --outDir $out .\05-patrones-diseno-counter-strike\builder\MainSala.ts
 *   node (Join-Path $out "MainSala.js")
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
