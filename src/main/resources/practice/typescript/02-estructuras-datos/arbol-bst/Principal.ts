// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\02-estructuras-datos\arbol-bst\Principal.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\02-estructuras-datos\arbol-bst\Principal.ts'; node "$env:TEMP\ct-ts\Principal.js"

import { Arbol } from "./Arbol";

/**
 * Cliente BST: una función genérica de comparación constituye el contrato.
 *
 * En vez de acoplar el árbol a números, se parametriza el tipo T.
 * El recorrido inorden presenta valores crecientes según ese contrato.
 * Sin balanceo la complejidad depende de la altura del árbol.
 */
const arbol = new Arbol<number>((a, b) => a - b);
for (const numero of [8, 3, 10, 1, 6, 4, 7]) arbol.insertar(numero);
console.log(arbol.inorden());
