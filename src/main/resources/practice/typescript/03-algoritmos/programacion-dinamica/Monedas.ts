// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit ARCHIVO.ts
// Ejecutar: Consulte Principal.ts

/** DP bottom-up de cambio mínimo: O(monto*denominaciones), O(monto) memoria. */
export function minimo(monedas: readonly number[], monto: number): number {
    if (!Number.isInteger(monto) || monto < 0 || !monedas.length ||
        monedas.some(x => !Number.isInteger(x) || x < 1)) throw Error("Entrada inválida");
    const imposible = monto + 1;
    const dp = Array<number>(monto + 1).fill(imposible);
    dp[0] = 0;
    for (let actual = 1; actual <= monto; actual++)
        for (const moneda of monedas)
            if (moneda <= actual) dp[actual] = Math.min(dp[actual], dp[actual-moneda] + 1);
    return dp[monto] === imposible ? -1 : dp[monto];
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\programacion-dinamica\MainMonedas.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainMonedas.js"
 * Nota: Monedas.ts es un componente; la salida corresponde al cliente MainMonedas.ts.
 * Salida esperada:
 *   Mínimo: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
