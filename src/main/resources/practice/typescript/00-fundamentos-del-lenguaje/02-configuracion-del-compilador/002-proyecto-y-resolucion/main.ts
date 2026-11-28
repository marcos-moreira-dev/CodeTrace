// Tema: Resolución de módulos
// La extensión .js señala el fichero de salida. El compilador resuelve el archivo util.ts que lo origina.
// Desde la carpeta de este ejercicio:
// Compilar: tsc -p .
// Ejecutar: node main.js
// Salida esperada:
// 12

import { doble } from './util.js';
console.log(doble(6));
