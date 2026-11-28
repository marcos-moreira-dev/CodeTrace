// Tema: type only imports and export.
// import type se elimina de la salida JavaScript. House describe la forma del objeto y no requiere un valor exportado.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module NodeNext --moduleResolution NodeNext 01.ts house.ts
// Ejecutar: node 01.js
// Salida esperada:
// Calle 1

import type { House } from './house.js';
const casa: House = { address: 'Calle 1' };
console.log(casa.address);
