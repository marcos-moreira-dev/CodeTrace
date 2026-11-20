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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
