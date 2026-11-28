// Tema:
// await using permite esperar la disposición asíncrona del recurso al finalizar su ámbito.

// lib.esnext.disposable.d.ts
interface AsyncDisposable {
    [Symbol.asyncDispose](): Promise<void>;
}
