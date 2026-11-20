// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\cuatro-reinas\cuatro_reinas.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\cuatro-reinas\cuatro_reinas.ts'; node "$env:TEMP\ct-ts\cuatro_reinas.js"

/**
 * Cliente de backtracking con cuatro reinas.
 *
 * Cada fila propone una columna. Si amenaza una reina anterior,
 * se poda la rama. Tras explorar, se deshace la asignación.
 * La complejidad de búsqueda es combinatoria en el peor caso.
 */
function resolver(n: number): number[][] {
    if (!Number.isInteger(n) || n < 1 || n > 12) throw Error("n");
    const parciales: number[] = [], soluciones: number[][] = [];
    function buscar(fila: number): void {
        if (fila === n) { soluciones.push([...parciales]); return; }
        for (let c = 0; c < n; c++) {
            if (!parciales.every((anterior, f) =>
                anterior !== c && Math.abs(anterior - c) !== fila - f)) continue;
            parciales.push(c);
            buscar(fila + 1);
            parciales.pop();
        }
    }
    buscar(0);
    return soluciones;
}
console.log(resolver(4));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\cuatro-reinas\cuatro_reinas.ts
 *   node "$env:TEMP\ct-ejemplo-ts\cuatro_reinas.js"
 * Salida esperada:
 *   [ [ 1, 3, 0, 2 ], [ 2, 0, 3, 1 ] ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
