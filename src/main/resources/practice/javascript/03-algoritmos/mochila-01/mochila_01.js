// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check ".\03-algoritmos\mochila-01\mochila_01.js"
// Ejecutar: node ".\03-algoritmos\mochila-01\mochila_01.js"

/**
 * Cliente de mochila 0/1 y subproblemas de programación dinámica.
 *
 * Para cada artículo, la tabla representa capacidad disponible.
 * Iterar descendiendo garantiza que un artículo no se elija dos
 * veces. Costo O(n*C) tiempo y O(C) memoria, seudopolinómico en C.
 */
function optimizar(pesos, valores, capacidad) {
    if (capacidad < 0 || pesos.length !== valores.length) throw Error("Entrada");
    const dp = Array(capacidad + 1).fill(0);
    pesos.forEach((peso, i) => {
        if (peso <= 0 || valores[i] < 0) throw Error("Artículo inválido");
        for (let c = capacidad; c >= peso; c--)
            dp[c] = Math.max(dp[c], dp[c - peso] + valores[i]);
    });
    return dp[capacidad];
}
console.log(optimizar([2, 3, 4, 5], [3, 4, 5, 8], 8));

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\mochila-01\mochila_01.js
 * Salida esperada:
 *   12
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
