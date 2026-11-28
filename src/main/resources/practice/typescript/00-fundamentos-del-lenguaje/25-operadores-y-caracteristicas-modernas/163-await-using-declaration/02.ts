// Tema: await using declaration.
// await using permite esperar la disposición asíncrona del recurso al finalizar su ámbito.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

// lib.esnext.disposable.d.ts
interface AsyncDisposable {
    [Symbol.asyncDispose](): Promise<void>;
}
