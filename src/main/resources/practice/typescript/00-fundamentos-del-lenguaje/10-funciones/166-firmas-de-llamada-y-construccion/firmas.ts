// Tema: firmas de llamada y construccion.
// Una firma de construcción se invoca con new; una firma de llamada se invoca directamente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 firmas.ts
// Ejecutar: node firmas.js
// Salida esperada:
// 6

type Llamada = (n:number)=>number;
type Constructor = new (n:number)=>{ valor:number };
class Caja { constructor(public valor:number) {} }
const crear: Constructor = Caja;
const doble: Llamada = n=>n*2;
console.log(new crear(doble(3)).valor);
