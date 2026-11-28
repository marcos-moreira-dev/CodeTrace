// Tema: async await.
// await suspende la función asíncrona sin bloquear el hilo; try/catch captura el rechazo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 async_await.ts
// Ejecutar: node async_await.js
// Salida esperada:
// 7
// fallo

async function valor():Promise<number>{return 7;}
async function main():Promise<void>{console.log(await valor()); try { await Promise.reject(new Error("fallo")); } catch(e){if(e instanceof Error)console.log(e.message);} }
void main();
