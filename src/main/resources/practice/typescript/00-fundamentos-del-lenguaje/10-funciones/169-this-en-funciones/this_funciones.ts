// Tema: this en funciones.
// El parámetro this es una anotación estática y no forma parte de los argumentos de ejecución. call establece el receptor.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 this_funciones.ts
// Ejecutar: node this_funciones.js
// Salida esperada:
// 7

function leer(this:{valor:number}):number { return this.valor; }
const contexto={valor:7};
console.log(leer.call(contexto));
