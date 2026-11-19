// CodeTrace | Referencia: TypeScript, Dijkstra didáctico O(V²)
// Compilar: npx tsc --target ES2022 dijkstra.ts
type Arista = { destino: number; costo: number };
function dijkstra(grafo: Arista[][], inicio: number): number[] {
    const distancia = Array(grafo.length).fill(Infinity);
    const visitado = Array(grafo.length).fill(false);
    distancia[inicio] = 0;
    for (let paso = 0; paso < grafo.length; paso++) {
        let mejor = -1;
        for (let i = 0; i < grafo.length; i++) {
            if (!visitado[i] && (mejor === -1 || distancia[i] < distancia[mejor])) {
                mejor = i;
            }
        }
        if (mejor === -1 || distancia[mejor] === Infinity) break;
        visitado[mejor] = true;
        for (const arista of grafo[mejor]) {
            if (arista.costo < 0) throw new Error("Peso negativo");
            distancia[arista.destino] = Math.min(
                distancia[arista.destino], distancia[mejor] + arista.costo);
        }
    }
    return distancia;
}
console.log(dijkstra([[{ destino: 1, costo: 2 }], []], 0));
