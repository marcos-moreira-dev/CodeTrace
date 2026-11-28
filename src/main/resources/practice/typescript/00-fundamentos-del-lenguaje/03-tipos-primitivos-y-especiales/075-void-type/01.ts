// Tema: Retorno void
// Una función que realiza un efecto y no devuelve un resultado útil tiene retorno void.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// Hello!

const sayHello = (): void => { console.log('Hello!'); };
sayHello();
