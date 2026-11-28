// Tema: computed and constant members.
// Un miembro calculado puede evaluarse en ejecución. La función retorna un valor fijo para una salida reproducible.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// 3

function valor(): number { return 3; }
enum Color { Red=1, Green=Math.pow(2,2), Blue=valor() }
console.log(Color.Blue);
