// Tema: esm.
// El package.json define ESM; la extensión .js es la del módulo emitido.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module NodeNext main.ts operaciones.ts
// Ejecutar: node main.js
// Salida esperada:
// 6 5

import suma,{doble} from './operaciones.js';
console.log(doble(3),suma(2,3));
