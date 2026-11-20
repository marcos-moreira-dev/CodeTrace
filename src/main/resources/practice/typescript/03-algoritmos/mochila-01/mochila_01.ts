// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\03-algoritmos\mochila-01\mochila_01.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\03-algoritmos\mochila-01\mochila_01.ts'; node "$env:TEMP\ct-ts\mochila_01.js"

/**
 * Cliente de mochila 0/1 por programación dinámica.
 *
 * Cada artículo puede escogerse a lo sumo una vez. El recorrido
 * descendente de capacidad evita usar repetidamente un artículo.
 * Tiempo O(n*C) y memoria O(C), seudopolinómico en C.
 */
function optimizar(pesos: number[], valores: number[], C: number): number {
    if (pesos.length !== valores.length || !Number.isInteger(C) || C < 0)
        throw Error("Entradas inválidas");
    const dp = Array<number>(C + 1).fill(0);
    for (let i = 0; i < pesos.length; i++) {
        const peso = pesos[i], valor = valores[i];
        if (!Number.isInteger(peso) || peso <= 0 || valor < 0)
            throw Error("Artículo inválido");
        for (let c = C; c >= peso; c--)
            dp[c] = Math.max(dp[c], dp[c - peso] + valor);
    }
    return dp[C];
}
console.log("Valor máximo:", optimizar([2, 3, 4, 5], [3, 4, 5, 8], 8));
