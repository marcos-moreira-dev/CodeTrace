// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainDeque.js"

import { DequeEnlazado } from "./DequeEnlazado";
/**
 * Cliente Deque: usar una sola secuencia en ambos extremos.
 *
 * Se estudian las cuatro operaciones constantes sobre nodos
 * dobles. En JS/TS no existe un Deque de tamaño arbitrario
 * tan directo como un Array, y Array.shift() puede ser O(n).
 */
const d=new DequeEnlazado<string>();
d.insertarPrimero("B");d.insertarPrimero("A");d.insertarUltimo("C");
if(d.quitarPrimero()!=="A"||d.quitarUltimo()!=="C"||d.quitarUltimo()!=="B")
    throw Error("Deque");
console.log("Deque correcta");
