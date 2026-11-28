// Tema: typescript fundamental comparison rules.
// Los argumentos genéricos usados en propiedades sí afectan la compatibilidad.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 15.ts
// Diagnóstico esperado: TS2367; no ejecutar.

interface X<T> {
    a: T;
}
let x: X<number> = { a: 1 };
let y: X<string> = { a: 'a' };
x === y; // Inválido as the type argument is used in the final structure
