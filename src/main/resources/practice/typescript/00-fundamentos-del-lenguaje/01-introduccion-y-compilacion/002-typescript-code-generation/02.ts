// Tema: Ejecución sin restricciones estáticas
// any permite reproducir la conducta de JavaScript aun con strict; el operador concatena cadenas. No es una práctica segura para una firma numérica.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// xy

const add = (a: any, b: any) => a + b;
const result = add('x', 'y');
console.log(result);
