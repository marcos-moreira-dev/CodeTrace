// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check ".\03-algoritmos\bfs_dfs.js"
// Ejecutar: node ".\03-algoritmos\bfs_dfs.js"

/**
 * Cliente de búsqueda en anchura (BFS) y profundidad (DFS).
 *
 * BFS usa una cola con índice de extracción para no pagar O(n)
 * por Array.shift. DFS utiliza una pila; ambos registran nodos
 * visitados para evitar ciclos. En lista de adyacencia: O(V+E).
 */
const grafo = new Map([
    ["A", ["B", "C"]], ["B", ["D"]], ["C", ["E"]], ["D", []], ["E", []]
]);
function bfs(g, origen) {
    const q = [origen], vistos = new Set([origen]), salida = [];
    for (let i = 0; i < q.length; i++) {
        const v = q[i];
        salida.push(v);
        for (const w of g.get(v) ?? []) if (!vistos.has(w)) {
            vistos.add(w);
            q.push(w);
        }
    }
    return salida;
}
function dfs(g, origen) {
    const pila = [origen], vistos = new Set(), salida = [];
    while (pila.length) {
        const v = pila.pop();
        if (vistos.has(v)) continue;
        vistos.add(v);
        salida.push(v);
        pila.push(...[...(g.get(v) ?? [])].reverse());
    }
    return salida;
}
console.log(bfs(grafo, "A"), dfs(grafo, "A"));
