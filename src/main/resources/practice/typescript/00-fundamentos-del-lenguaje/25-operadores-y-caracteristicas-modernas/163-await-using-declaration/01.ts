// Tema: await using declaration.
// await using espera la promesa de liberación al finalizar el ámbito asíncrono.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// trabajo
// cerrado

function abrir(): AsyncDisposable { return { async [Symbol.asyncDispose]() { console.log("cerrado"); } }; }
async function doWorkAsync(): Promise<void> {
    await using work = abrir();
    console.log("trabajo");
}
void doWorkAsync();
