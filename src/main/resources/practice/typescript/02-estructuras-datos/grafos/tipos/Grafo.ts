// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde copia (PowerShell): tsc --strict --target ES2022 --module commonjs --noEmit '.\02-estructuras-datos\grafos\tipos\Grafo.ts'
// Ejecutar: usar desde Principal.ts de la misma carpeta

/**
 * Grafo simple dirigido/no dirigido y ponderado/no ponderado.
 *
 * Se guardan adyacencias en Map<string,Map<string,number>>:
 * O(V+E) memoria, especialmente adecuada para grafos dispersos.
 * Los tipos discriminados de TS protegen la API estáticamente,
 * pero se validan los pesos también en ejecución.
 */
export type Orientacion = "dirigido" | "no-dirigido";

export class Grafo {
    private readonly ady = new Map<string, Map<string, number>>();

    constructor(
        readonly orientacion: Orientacion,
        readonly ponderado: boolean
    ) {}

    vertice(id: string): void {
        if (!id) throw Error("ID vacío");
        if (!this.ady.has(id)) this.ady.set(id, new Map());
    }

    conectar(desde: string, hasta: string, peso = 1): void {
        if (desde === hasta || !Number.isFinite(peso)) throw Error("Arista inválida");
        if (!this.ponderado && peso !== 1) throw Error("No es ponderado");
        this.vertice(desde);
        this.vertice(hasta);
        this.ady.get(desde)!.set(hasta, peso);
        if (this.orientacion === "no-dirigido")
            this.ady.get(hasta)!.set(desde, peso);
    }

    vecinos(id: string): Map<string, number> {
        const vecinos = this.ady.get(id);
        if (!vecinos) throw Error("Vértice ausente");
        return new Map(vecinos);
    }

    vertices(): string[] { return [...this.ady.keys()]; }

    componentes(): number {
        if (this.orientacion === "dirigido")
            throw Error("Conectividad dirigida es otro concepto");
        const vistos = new Set<string>();
        let n = 0;
        for (const inicio of this.ady.keys()) {
            if (vistos.has(inicio)) continue;
            n++;
            const cola = [inicio];
            vistos.add(inicio);
            for (let i = 0; i < cola.length; i++)
                for (const vecino of this.ady.get(cola[i])!.keys())
                    if (!vistos.has(vecino)) {
                        vistos.add(vecino);
                        cola.push(vecino);
                    }
        }
        return n;
    }
}
