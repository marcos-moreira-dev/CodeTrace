// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainListaSimple.js"

import { ListaSimple } from "./lista_simple";

/**
 * Cliente de lista enlazada: nodos con referencias, no memoria contigua.
 *
 * Insertar al comienzo cuesta O(1) independientemente del tamaño
 * actual. No se dispone aquí de acceso indexado, una operación que
 * en una lista enlazada requeriría recorrer nodos.
 */
const lista = new ListaSimple<string>();
lista.insertarInicio("A");
lista.insertarInicio("B");
console.log("Lista con dos nodos insertados");

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\linked-list\simple\MainListaSimple.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainListaSimple.js"
 * Salida esperada:
 *   Lista con dos nodos insertados
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
