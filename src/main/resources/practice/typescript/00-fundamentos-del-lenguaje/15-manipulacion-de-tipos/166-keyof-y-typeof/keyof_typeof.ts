// Tema: keyof y typeof.
// typeof obtiene el tipo del valor; keyof obtiene la unión de claves y el acceso indexado conserva el tipo asociado.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 keyof_typeof.ts
// Ejecutar: node keyof_typeof.js
// Salida esperada:
// Ana 20

const valores={nombre:"Ana",edad:20};
type Clave=keyof typeof valores;
function leer<K extends Clave>(clave:K): (typeof valores)[K] { return valores[clave]; }
console.log(leer("nombre"),leer("edad"));
