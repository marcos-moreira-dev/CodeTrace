// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde copia (PowerShell): tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\grafos\dijkstra\cuadratico.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\grafos\dijkstra\cuadratico.ts'; node "$env:TEMP\ct-ts\cuadratico.js"

/**
 * Cliente Dijkstra O(V²+E) sin una cola de prioridad.
 *
 * Esta versión explora todos los vértices al escoger el
 * siguiente de menor distancia. Es fácil de estudiar y puede
 * servir en grafos pequeños/densos. Contrasta con la versión
 * Java y Python que utilizan un heap y mejoran el costo.
 *
 * Se exige que TODOS los pesos sean finitos y no negativos.
 */
type Grafo = Map<string, Map<string, number>>;
function dijkstra(g: Grafo, inicio: string): Map<string, number> {
    if (!g.has(inicio)) throw Error("Origen ausente");
    const vertices = new Set<string>(g.keys());
    for (const vecinos of g.values())
        for (const [destino, peso] of vecinos) {
            if (!Number.isFinite(peso) || peso < 0) throw Error("Peso no permitido");
            vertices.add(destino);
        }
    const dist = new Map([...vertices].map(v => [v, Infinity] as const));
    const visto = new Set<string>();
    dist.set(inicio, 0);
    for (let k = 0; k < vertices.size; k++) {
        let elegido: string | undefined;
        for (const v of vertices)
            if (!visto.has(v) && (elegido === undefined || dist.get(v)! < dist.get(elegido)!))
                elegido = v;
        if (elegido === undefined || dist.get(elegido) === Infinity) break;
        visto.add(elegido);
        for (const [w, peso] of g.get(elegido) ?? [])
            if (dist.get(elegido)! + peso < dist.get(w)!)
                dist.set(w, dist.get(elegido)! + peso);
    }
    return dist;
}
const red: Grafo = new Map([
    ["A", new Map([["B", 4], ["C", 1]])],
    ["B", new Map([["D", 1]])],
    ["C", new Map([["B", 2], ["D", 5]])],
    ["D", new Map([["E", 3]])], ["E", new Map()]
]);
console.log("A->E =", dijkstra(red, "A").get("E"));
if (dijkstra(red, "A").get("E") !== 7) throw Error("Ruta incorrecta");
