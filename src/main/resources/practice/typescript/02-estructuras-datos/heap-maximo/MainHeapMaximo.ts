// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainHeapMaximo.js"

import { HeapMaximo } from "./HeapMaximo";
/**
 * Cliente Max Heap: no es pila, ni cola FIFO, ni BST.
 *
 * La relación padre>=hijos NO implica que el arreglo completo
 * esté ordenado. Solo la raíz contiene siempre el máximo;
 * extraer reorganiza el arreglo mediante sift-down.
 */
const heap=new HeapMaximo();
[4,1,9,7,2].forEach(n=>heap.insertar(n));
if(Array.from({length:5},()=>heap.extraer()).join(",")!=="9,7,4,2,1")
    throw Error("Heap máximo");
console.log("Heap máximo OK");

/*
 * Salida esperada de consola:
 *   Heap máximo OK
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
