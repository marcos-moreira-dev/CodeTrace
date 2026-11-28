// Tema: declaraciones y runtime.
// La declaración .d.ts informa al compilador; biblioteca.js aporta la implementación real.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module NodeNext main.ts biblioteca.d.ts
// Ejecutar: node main.js
// Salida esperada:
// 8

import {doble} from './biblioteca.js';
console.log(doble(4));
