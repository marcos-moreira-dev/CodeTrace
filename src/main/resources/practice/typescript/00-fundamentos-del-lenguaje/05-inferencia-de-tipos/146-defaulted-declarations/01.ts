// Tema: defaulted declarations.
// Los valores predeterminados permiten inicializar parámetros o declaraciones cuando no se proporciona el valor correspondiente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// Hello, Anonymous!
// Hello, John!

function greet(name: string = 'Anonymous'): void {
    console.log(`Hello, ${name}!`);
}
greet(); // Hello, Anonymous!
greet('John'); // Hello, John!
