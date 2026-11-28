// Tema: iterable personalizado.
// Symbol.iterator devuelve un iterador; el protocolo permite usar for-of o spread.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 iterable.ts
// Ejecutar: node iterable.js
// Salida esperada:
// 1,2,3

const rango:Iterable<number>={*[Symbol.iterator](){yield 1;yield 2;yield 3;}};
console.log([...rango].join(","));
