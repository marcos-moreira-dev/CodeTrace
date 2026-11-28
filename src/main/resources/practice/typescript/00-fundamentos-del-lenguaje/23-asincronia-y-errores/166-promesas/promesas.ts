// Tema: promesas.
// then transforma el resultado y catch gestiona un rechazo. Las dos cadenas se resuelven en el orden de sus microtareas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 promesas.ts
// Ejecutar: node promesas.js
// Salida esperada:
// fallo
// 6

Promise.resolve(2).then(n=>n*3).then(n=>console.log(n));
Promise.reject(new Error("fallo")).catch((e:Error)=>console.log(e.message));
