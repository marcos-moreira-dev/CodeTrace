// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit ARCHIVO.ts
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir dist ARCHIVO.ts; node dist/ARCHIVO.js

/**
 * Cliente de genéricos y funciones de comparación.
 * La función ordena una copia sin modificar el original. Su criterio
 * establece precio primero e identificador en caso de empate.
 * El parámetro T permite cambiar el tipo sin duplicar el algoritmo.
 */
type Comparador<T> = (a: T, b: T) => number;
type Producto = { id: string; precio: number };

function ordenar<T>(items: readonly T[], cmp: Comparador<T>): T[] {
    return [...items].sort(cmp);
}
const datos: Producto[] = [{id:"B", precio:2},{id:"A", precio:2},{id:"C", precio:1}];
console.log(ordenar(datos, (a, b) => a.precio - b.precio || a.id.localeCompare(b.id)));
