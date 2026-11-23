// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js
// Compilar: tsc --strict --target ES2022 --module commonjs (véase guía)
// Dominio: Counter-Strike — actualizaciones del HUD mediante callbacks tipados.
// Patrón: Observer.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.

export type Bando = "CT" | "T";
export type EventoMarcador = Readonly<{ equipo: Bando; puntos: number }>;
export type Receptor = (evento: EventoMarcador) => void;
export class MarcadorObservable {
    private readonly receptores = new Set<Receptor>();
    suscribir(receptor: Receptor): () => void {
        this.receptores.add(receptor);
        // Devolvemos una función de limpieza, útil al desmontar una vista web.
        return () => { this.receptores.delete(receptor); };
    }
    anotar(equipo: Bando, puntos: number): void {
        const evento: EventoMarcador = { equipo, puntos };
        // Cada pantalla recibe el evento; no se delega a un único responsable.
        for (const receptor of [...this.receptores]) receptor(evento);
    }
}

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
