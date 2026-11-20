// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainHashAbierto.js"

import { HashAbierto } from "./HashAbierto";
/**
 * Cliente hash abierto: dos claves en dos casillas, colisiones
 * forzadas por capacidad 2, sobrescritura de valor y saturación.
 *
 * El índice hash no es una identidad: se debe comparar la cadena
 * original y aplicar sondeo. Contrastar con tablas por encadenamiento.
 */
const t=new HashAbierto(2);
t.poner("A",1);t.poner("B",2);t.poner("A",3);
if(t.buscar("A")!==3||t.buscar("B")!==2||
    t.buscar("Z")!==undefined||t.cantidad!==2)throw Error("Hash");
let lleno=false;
try{t.poner("C",4);}catch{lleno=true;}
if(!lleno)throw Error("Debería estar llena");
console.log("Sondeo comprobado");
