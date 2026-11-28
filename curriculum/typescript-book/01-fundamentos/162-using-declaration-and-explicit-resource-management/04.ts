// Tema:
// using registra un recurso para liberar automáticamente cuando se abandona su ámbito si
// implementa el protocolo de disposición.

// lib.esnext.disposable.d.ts
interface Disposable {
    [Symbol.dispose](): void;
}
