// Tema: jsx elemental.
// JSX describe una llamada a la fábrica h. El ejercicio no utiliza React ni manipula el DOM.
// Desde la carpeta de este ejercicio:
// Compilar: tsc -p .
// Ejecutar: node jsx.js
// Salida esperada:
// <saludo>Hola</saludo>

import {h} from './factory.js';
const vista=<saludo>Hola</saludo>;
console.log(vista);
