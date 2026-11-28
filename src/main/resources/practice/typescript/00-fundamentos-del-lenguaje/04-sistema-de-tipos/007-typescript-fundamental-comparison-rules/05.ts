// Tema: typescript fundamental comparison rules.
// La asignabilidad depende de las propiedades, los parámetros y retornos de las funciones, y las restricciones de miembros privados o protegidos. Los ejemplos muestran asignaciones aceptadas y rechazadas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 05.ts
// Ejecutar: node 05.js
// Salida esperada:
// 1x,2x,3x

console.log([1,2,3].map(element => element + "x").join(","));
