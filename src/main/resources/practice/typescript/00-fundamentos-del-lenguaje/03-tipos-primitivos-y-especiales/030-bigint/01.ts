// Tema: Enteros grandes
// bigint permite enteros de precisión arbitraria. No se mezcla directamente con number en operaciones aritméticas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// 18014398509481982

const x: bigint = BigInt(9007199254740991);
const y: bigint = 9007199254740991n;
console.log((x + y).toString());
