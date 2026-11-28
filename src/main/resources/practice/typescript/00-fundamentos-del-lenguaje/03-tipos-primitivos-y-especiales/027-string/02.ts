// Tema: Plantillas literales
// Las comillas invertidas permiten interpolar expresiones y conservar saltos de línea.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// Hola Ana,
// bienvenida

const nombre = 'Ana';
const sentence: string = `Hola ${nombre},
bienvenida`;
console.log(sentence);
