// Tema: Claves symbol
// Cada Symbol crea una identidad distinta, incluso si la descripción coincide. Un índice symbol permite propiedades con esas claves.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// 123
// 456

type Obj = { [sym: symbol]: number };
const a = Symbol('a');
const b = Symbol('b');
const obj: Obj = {};
obj[a] = 123;
obj[b] = 456;
console.log(obj[a]);
console.log(obj[b]);
