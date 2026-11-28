// Tema: Representación de números
// number usa números de punto flotante IEEE 754. Los prefijos de base cambian la notación, no el tipo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// 10 40973 10 411

const decimal: number = 10;
const hexadecimal: number = 0xa00d;
const binary: number = 0b1010;
const octal: number = 0o633;
console.log(decimal, hexadecimal, binary, octal);
