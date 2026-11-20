// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainListaDoble.js"

import { ListaDoble } from "./ListaDoble";
/**
 * Cliente Linked List doble, comparado con lista simple.
 *
 * La doble referencia cuesta memoria por nodo, pero habilita
 * navegación hacia atrás y extracción del último nodo en O(1).
 * El cliente no manipula enlaces; valida ambos sentidos.
 */
const lista=new ListaDoble<string>();
["A","B","C"].forEach(x=>lista.agregarFinal(x));
if(lista.adelante().join(",")!=="A,B,C"||lista.atras().join(",")!=="C,B,A")
    throw Error("Lista doble");
if(lista.quitarFinal()!=="C"||lista.cantidad!==2)throw Error("Quitar");
console.log(lista.adelante());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\linked-list\doble\MainListaDoble.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainListaDoble.js"
 * Salida esperada:
 *   [ 'A', 'B' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
