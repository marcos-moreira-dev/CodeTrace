// Tema: object types.
// Un tipo de objeto describe las propiedades y métodos que debe satisfacer una estructura.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// 6

const sum = (x: { a: number; b: number }) => x.a + x.b;
console.log(sum({ a: 5, b: 1 }));
