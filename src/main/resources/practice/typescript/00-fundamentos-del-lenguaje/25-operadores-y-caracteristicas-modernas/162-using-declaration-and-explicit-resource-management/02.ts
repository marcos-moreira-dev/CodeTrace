// Tema: using declaration and explicit resource management.
// using llama Symbol.dispose al salir del bloque. Se requiere un runtime que implemente el símbolo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// 1
// 2
// disposed
// 3

function doWork(): Disposable { return { [Symbol.dispose]() { console.log("disposed"); } }; }
console.log(1);
{ using work = doWork(); console.log(2); }
console.log(3);
