// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\grafos\expansion-minima\kruskal.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\grafos\expansion-minima\kruskal.ts'; node "$env:TEMP\ct-ts\kruskal.js"

/**
 * Cliente de Kruskal y Union-Find para un grafo NO dirigido.
 *
 * Un árbol de expansión mínima conecta los nodos sin ciclos
 * y minimiza costo total; no garantiza el camino más corto entre
 * dos vértices. Las aristas se ordenan y se unen componentes
 * diferentes. Complejidad O(E log E), espacio O(V+E).
 */
type Arista = Readonly<{ a: number; b: number; peso: number }>;

class UnionFind {
    private padre: number[];
    private tamaño: number[];
    componentes: number;

    constructor(n: number) {
        if (!Number.isInteger(n) || n <= 0) throw Error("Vértices");
        this.padre = Array.from({ length: n }, (_, i) => i);
        this.tamaño = Array(n).fill(1);
        this.componentes = n;
    }

    encontrar(x: number): number {
        if (this.padre[x] !== x) this.padre[x] = this.encontrar(this.padre[x]);
        return this.padre[x];
    }

    unir(a: number, b: number): boolean {
        let ra = this.encontrar(a), rb = this.encontrar(b);
        if (ra === rb) return false;
        if (this.tamaño[ra] < this.tamaño[rb]) [ra, rb] = [rb, ra];
        this.padre[rb] = ra;
        this.tamaño[ra] += this.tamaño[rb];
        this.componentes--;
        return true;
    }
}

function kruskal(vertices: number, aristas: readonly Arista[]) {
    const uf = new UnionFind(vertices);
    const elegidas: Arista[] = [];
    let total = 0;
    for (const e of [...aristas].sort((x, y) => x.peso - y.peso)) {
        if (![e.a, e.b].every(v => Number.isInteger(v) && v >= 0 && v < vertices)
            || e.a === e.b || !Number.isFinite(e.peso)) throw Error("Arista inválida");
        if (uf.unir(e.a, e.b)) {
            elegidas.push(e);
            total += e.peso;
        }
    }
    return { elegidas, total, componentes: uf.componentes };
}

const red: Arista[] = [
    { a: 0, b: 1, peso: 3 }, { a: 0, b: 2, peso: 1 },
    { a: 1, b: 2, peso: 2 }, { a: 1, b: 3, peso: 4 },
    { a: 2, b: 3, peso: 5 }, { a: 3, b: 4, peso: 1 },
    { a: 2, b: 4, peso: 8 }
];
const resultado = kruskal(5, red);
console.log(resultado);
if (resultado.total !== 8 || resultado.componentes !== 1
    || resultado.elegidas.length !== 4) throw Error("MST incorrecto");
