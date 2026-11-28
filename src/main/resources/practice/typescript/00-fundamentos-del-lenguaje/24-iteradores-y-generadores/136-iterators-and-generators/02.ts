// Tema: iterators and generators.
// Un iterador entrega valores sucesivos; una función generadora usa yield para suspender y reanudar su ejecución.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// 1
// 2
// 3
// 4
// 5

function* numberGenerator(start: number, end: number): Generator<number> {
    for (let i = start; i <= end; i++) {
        yield i;
    }
}

const generator = numberGenerator(1, 5);

for (const num of generator) {
    console.log(num);
}
