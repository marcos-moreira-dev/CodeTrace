// Tema: using declaration and explicit resource management.
// using registra un recurso para liberar automáticamente cuando se abandona su ámbito si implementa el protocolo de disposición.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable 04.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

// lib.esnext.disposable.d.ts
interface Disposable {
    [Symbol.dispose](): void;
}
