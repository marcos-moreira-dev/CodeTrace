// Tema: generador asincrono.
// Un generador asíncrono entrega valores a medida que se solicitan y for await espera cada resultado.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 generador_async.ts
// Ejecutar: node generador_async.js
// Salida esperada:
// 1
// 2

async function* datos():AsyncGenerator<number>{yield 1;yield 2;}
async function main(){for await(const n of datos())console.log(n);}
void main();
