// Tema: Estrechamiento de unknown
// typeof comprueba ambos operandos. Solo se permite sumarlos cuando los dos son number; los demás casos retornan undefined.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// 3
// undefined

const add = (a: unknown, b: unknown): number | undefined =>
    typeof a === 'number' && typeof b === 'number' ? a + b : undefined;
console.log(add(1, 2));
console.log(add('x', 2));
