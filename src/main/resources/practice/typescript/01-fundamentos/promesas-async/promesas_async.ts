// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\01-fundamentos\promesas-async\promesas_async.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\01-fundamentos\promesas-async\promesas_async.ts'; node "$env:TEMP\ct-ts\promesas_async.js"

/**
 * Cliente de Promise, await y errores asíncronos.
 *
 * Una función async siempre devuelve una promesa. await suspende
 * su continuación lógica sin bloquear el event loop. Los errores
 * se propagan como promesas rechazadas y requieren tratamiento.
 * El ejemplo no usa Internet ni un servidor.
 */
async function calcularTotal(importe: number): Promise<number> {
    if (importe < 0) throw new Error("Importe negativo");
    return importe * 1.15;
}
async function main(): Promise<void> {
    try {
        console.log("Total:", await calcularTotal(20));
    } catch (error) {
        console.error(error);
    }
}
void main();
