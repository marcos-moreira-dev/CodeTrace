// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — avisos de rescate, minimapa e inventario.
// Patrón: Chain of Responsibility.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export type TipoAviso = "rehen" | "rival" | "equipo" | "otro";
export interface Aviso { readonly tipo: TipoAviso; readonly detalle: string; }
export class Manejador {
    private siguiente?: Manejador;
    constructor(private readonly tipo: TipoAviso, private readonly prefijo: string) {}
    enlazar(otro: Manejador): Manejador { this.siguiente = otro; return otro; }
    atender(aviso: Aviso): string {
        // El mismo aviso pasa al siguiente responsable si no coincide su tipo.
        if (aviso.tipo === this.tipo) return `${this.prefijo}: ${aviso.detalle}`;
        return this.siguiente?.atender(aviso) ?? `Sin responsable: ${aviso.detalle}`;
    }
}
export function crearCadena(): Manejador {
    const primero = new Manejador("rehen", "Rescate");
    primero.enlazar(new Manejador("rival", "Reconocimiento"))
        .enlazar(new Manejador("equipo", "Inventario"));
    return primero;
}

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
