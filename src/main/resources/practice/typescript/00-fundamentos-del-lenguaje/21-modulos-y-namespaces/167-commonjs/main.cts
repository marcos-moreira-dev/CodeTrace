// Tema: commonjs.
// Los archivos .cts se emiten como .cjs, independientemente del tipo de paquete.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module NodeNext main.cts operaciones.cts
// Ejecutar: node main.cjs
// Salida esperada:
// 8

import {doble} from './operaciones.cjs';
console.log(doble(4));
