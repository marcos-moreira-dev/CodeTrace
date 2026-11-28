// Tema: Anotación de parámetros y retorno
// La función recibe dos números y devuelve un número. La anotación no cambia la suma durante la ejecución.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// 5

const sum = (a: number, b: number): number => a + b;
console.log(sum(2, 3));
