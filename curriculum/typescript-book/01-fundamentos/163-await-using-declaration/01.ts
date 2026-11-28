// Tema:
// await using permite esperar la disposición asíncrona del recurso al finalizar su ámbito.

async function doWorkAsync() {
    await using work = doWorkAsync(); // Resource is declared
} // Resource is disposed (e.g., `await work[Symbol.asyncDispose]()` is evaluated)
