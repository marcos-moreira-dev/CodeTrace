// Tema: type from module.
// typeof import obtiene el tipo de una exportación; la importación normal trae el valor real.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module NodeNext --moduleResolution NodeNext 01.ts calc.ts
// Ejecutar: node 01.js
// Salida esperada:
// 3

import { add } from './calc.js';
type Suma = typeof import('./calc.js').add;
const operacion: Suma = add;
console.log(operacion(1, 2));
