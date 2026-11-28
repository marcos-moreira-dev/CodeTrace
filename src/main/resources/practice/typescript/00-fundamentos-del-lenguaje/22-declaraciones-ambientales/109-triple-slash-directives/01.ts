// Tema: triple slash directives.
// La ruta es relativa al archivo y debe señalar una declaración existente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// 8080

/// <reference path="./declaration.d.ts" />
const config: Configuracion = { puerto: 8080 };
console.log(config.puerto);
