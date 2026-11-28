// Tema: El tipo any
// any desactiva comprobaciones sobre el valor: permite reasignar tipos y usar miembros que quizá no existan. Preferir unknown cuando el tipo se desconoce.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// true
// 7

let value: any = true;
console.log(value);
value = 7;
console.log(value);
