// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\bfs-dfs\bfs_dfs.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\bfs-dfs\bfs_dfs.ts'; node "$env:TEMP\ct-ts\bfs_dfs.js"

/**
 * Cliente de BFS y DFS: recorrer grafos con cola y pila.
 *
 * BFS descubre vértices por distancia en aristas y DFS explora
 * una rama antes de volver. Ambos usan un conjunto de visitados.
 * Con listas de adyacencia la complejidad es O(V+E), memoria O(V).
 */
type Grafo = Map<string, string[]>;
function bfs(g: Grafo, inicio: string): string[] {
    if (!g.has(inicio)) throw Error("Origen ausente");
    const cola = [inicio], vistos = new Set([inicio]), salida: string[] = [];
    for (let i = 0; i < cola.length; i++) {
        const actual = cola[i];
        salida.push(actual);
        for (const vecino of g.get(actual) ?? []) {
            if (vistos.has(vecino)) continue;
            vistos.add(vecino);
            cola.push(vecino);
        }
    }
    return salida;
}
function dfs(g: Grafo, inicio: string): string[] {
    if (!g.has(inicio)) throw Error("Origen ausente");
    const pila = [inicio], vistos = new Set<string>(), salida: string[] = [];
    while (pila.length) {
        const actual = pila.pop()!;
        if (vistos.has(actual)) continue;
        vistos.add(actual);
        salida.push(actual);
        pila.push(...[...(g.get(actual) ?? [])].reverse());
    }
    return salida;
}
const g: Grafo = new Map([
    ["A", ["B", "C"]], ["B", ["D"]], ["C", ["E"]], ["D", []], ["E", []]
]);
console.log("BFS:", bfs(g, "A"), "DFS:", dfs(g, "A"));
