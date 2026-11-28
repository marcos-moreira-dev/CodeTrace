// Tema: promesas combinadas.
// all espera todos los resultados, allSettled devuelve estados y race observa la primera promesa resuelta o rechazada.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 promesas_combinadas.ts
// Ejecutar: node promesas_combinadas.js
// Salida esperada:
// 1,2
// fulfilled,rejected
// 1

async function main():Promise<void>{const a=Promise.resolve(1),b=Promise.resolve(2);console.log((await Promise.all([a,b])).join(","));const r=await Promise.allSettled([a,Promise.reject("x")]);console.log(r.map(x=>x.status).join(","));console.log(await Promise.race([a,b]));}
void main();
