// Tema: tsdocs jsdoc reference.
// JSDoc documenta parámetros y retorno; las anotaciones TypeScript expresan sus tipos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// 100

/**
 * Calcula una potencia.
 * @param base Base de la operación.
 * @param exponent Exponente.
 * @returns Resultado numérico.
 */
function power(base:number,exponent:number):number { return Math.pow(base,exponent); }
console.log(power(10,2));
