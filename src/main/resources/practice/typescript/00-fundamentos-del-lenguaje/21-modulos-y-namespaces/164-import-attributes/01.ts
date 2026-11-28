// Tema: import attributes.
// El atributo type indica que el módulo contiene JSON. Requiere ESM y soporte de atributos de importación.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module ESNext --moduleResolution Bundler --resolveJsonModule 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// demo

import config from './config.json' with { type: 'json' };
console.log(config.nombre);
