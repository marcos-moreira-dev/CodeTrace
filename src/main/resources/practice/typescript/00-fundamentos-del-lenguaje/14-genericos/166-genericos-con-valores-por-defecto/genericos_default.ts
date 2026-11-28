// Tema: genericos con valores por defecto.
// El argumento genérico por defecto se aplica si el cliente omite T.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 genericos_default.ts
// Ejecutar: node genericos_default.js
// Salida esperada:
// hola 4

interface Caja<T=string> { valor:T }
const texto: Caja = {valor:"hola"};
const numero: Caja<number>={valor:4};
console.log(texto.valor,numero.valor);
