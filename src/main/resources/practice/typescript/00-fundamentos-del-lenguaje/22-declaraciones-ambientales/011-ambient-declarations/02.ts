// Tema: ambient declarations.
// La directiva incorpora declaraciones locales; una interfaz no requiere una implementación externa.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// hola

/// <reference path="./library-types.d.ts" />
const contrato: Saludo = { mensaje: "hola" };
console.log(contrato.mensaje);
