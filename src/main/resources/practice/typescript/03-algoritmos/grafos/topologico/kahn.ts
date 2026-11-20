// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde copia (PowerShell): tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\grafos\topologico\kahn.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\grafos\topologico\kahn.ts'; node "$env:TEMP\ct-ts\kahn.js"

/**
 * Cliente de orden topológico en un DAG (Kahn).
 *
 * Una arista A->B significa que B requiere completar A.
 * Se parte de todos los nodos de grado de entrada cero,
 * eliminándolos y liberando nuevos nodos. Si queda un ciclo,
 * no hay orden válido. Complejidad O(V+E), espacio O(V+E).
 */
function ordenar(grafo: Map<string, string[]>): string[] {
    const grados = new Map<string, number>();
    for (const [v, vecinos] of grafo) {
        if (!grados.has(v)) grados.set(v, 0);
        for (const w of vecinos) grados.set(w, grados.get(w) ?? 0);
    }
    for (const vecinos of grafo.values()) {
        for (const w of vecinos) grados.set(w, grados.get(w)! + 1);
    }
    const cola = [...grados.keys()].filter(v => grados.get(v) === 0);
    const orden: string[] = [];
    for (let i = 0; i < cola.length; i++) {
        const v = cola[i];
        orden.push(v);
        for (const w of grafo.get(v) ?? []) {
            const nuevo = grados.get(w)! - 1;
            grados.set(w, nuevo);
            if (nuevo === 0) cola.push(w);
        }
    }
    if (orden.length !== grados.size) throw Error("Ciclo en dependencias");
    return orden;
}
const fases = new Map<string, string[]>([
    ["Fuentes", ["Compilar"]],
    ["Compilar", ["Pruebas", "Empaquetar"]],
    ["Pruebas", ["Desplegar"]],
    ["Empaquetar", ["Desplegar"]],
    ["Desplegar", []]
]);
console.log("Orden permitido:", ordenar(fases));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\grafos\topologico\kahn.ts
 *   node "$env:TEMP\ct-ejemplo-ts\kahn.js"
 * Salida esperada:
 *   Orden permitido: [ 'Fuentes', 'Compilar', 'Pruebas', 'Empaquetar', 'Desplegar' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
