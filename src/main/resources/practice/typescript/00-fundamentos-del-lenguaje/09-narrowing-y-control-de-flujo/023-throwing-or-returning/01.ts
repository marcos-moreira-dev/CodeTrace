// Tema: throwing or returning.
// Una salida anticipada permite descartar las alternativas que no pueden alcanzar el código siguiente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

let x: number | undefined = 10;

if (x === undefined) {
    throw 'error';
}
x += 100;
