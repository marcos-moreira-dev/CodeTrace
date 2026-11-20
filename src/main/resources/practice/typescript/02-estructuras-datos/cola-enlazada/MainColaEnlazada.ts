// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainColaEnlazada.js"

import { ColaEnlazada } from "./ColaEnlazada";
/**
 * Cliente cola enlazada: sin capacidad fija, crece con los nodos.
 *
 * Esta cola ofrece inserción y extracción O(1) y se contrasta
 * con la cola circular de un arreglo. La prueba vacía todo el
 * contenido y vuelve a encolar para revisar el último puntero.
 */
const q=new ColaEnlazada<string>();
q.encolar("A");q.encolar("B");
if(q.desencolar()!=="A"||q.desencolar()!=="B")throw Error("FIFO");
q.encolar("C");
if(q.desencolar()!=="C"||q.cantidad!==0)throw Error("Tail");
console.log("Cola enlazada verificada");
