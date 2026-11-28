// Tema: Primitivo y objeto envolvente
// String como constructor crea un objeto; String como función produce un primitivo. No modificar prototipos globales para ilustrarlo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// string
// object
// A

const primitive = String('A');
const boxed = new String('A');
console.log(typeof primitive);
console.log(typeof boxed);
console.log(boxed.valueOf());
