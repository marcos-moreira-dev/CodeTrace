// Tema: parametros.
// Los parámetros por defecto se aplican cuando el argumento es undefined. Rest recoge argumentos adicionales y ? permite omitir el parámetro.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 parametros.ts
// Ejecutar: node parametros.js
// Salida esperada:
// 15 invitado

function sumar(base=10,...valores:number[]):number { return valores.reduce((a,b)=>a+b,base); }
function saludo(nombre?:string):string { return nombre ?? "invitado"; }
console.log(sumar(undefined,2,3),saludo());
