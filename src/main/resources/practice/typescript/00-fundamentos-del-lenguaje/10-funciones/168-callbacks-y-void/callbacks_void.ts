// Tema: callbacks y void.
// El contrato void de un callback permite descartar un valor retornado por su implementación.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 callbacks_void.ts
// Ejecutar: node callbacks_void.js
// Salida esperada:
// 3

const numeros:number[]=[];
const callback:(n:number)=>void = n=>numeros.push(n);
callback(3);
console.log(numeros[0]);
