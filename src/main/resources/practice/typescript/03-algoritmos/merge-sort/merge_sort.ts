// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\merge-sort\merge_sort.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\merge-sort\merge_sort.ts'; node "$env:TEMP\ct-ts\merge_sort.js"

/**
 * Cliente de Merge Sort: divisiones recursivas y mezcla estable.
 *
 * Ordena cada mitad y fusiona los resultados. O(log n) niveles
 * con O(n) trabajo por nivel producen O(n log n) tiempo y O(n)
 * memoria. Esta implementación conserva la entrada original.
 */
function ordenar(datos: readonly number[]): number[] {
    if (datos.length <= 1) return [...datos];
    const m = Math.floor(datos.length / 2);
    const izq = ordenar(datos.slice(0, m));
    const der = ordenar(datos.slice(m));
    const salida: number[] = [];
    let i = 0, j = 0;
    while (i < izq.length && j < der.length) {
        salida.push(izq[i] <= der[j] ? izq[i++] : der[j++]);
    }
    return [...salida, ...izq.slice(i), ...der.slice(j)];
}
console.log(ordenar([9, 5, 2, 8, 1, 3]));
