// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainPila.js"

import { Pila } from "./pila";

/**
 * Cliente de pila LIFO implementada mediante arreglo JavaScript.
 *
 * push/pop cuestan O(1) amortizado; el valor undefined marca
 * ausencia, por lo que debe distinguirse de un T que podría
 * contener undefined. El ejemplo usa cadenas no nulas.
 */
const pila = new Pila<string>();
["A", "B", "C"].forEach(x => pila.apilar(x));
if (pila.desapilar() !== "C" || pila.desapilar() !== "B" ||
    pila.size() !== 1) throw Error("LIFO");
console.log("Pila correcta");
