// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainTrie.js"

import { Trie } from "./Trie";
/**
 * Cliente Trie: prefijo no significa palabra insertada.
 *
 * Map<string,Nodo> evita fijar un alfabeto numérico. TypeScript
 * transpila los tipos; la estructura en ejecución es Map de JS.
 * Cada consulta visita los caracteres del texto recibido.
 */
const t=new Trie();
t.insertar("casa");t.insertar("casco");
if(!t.prefijo("cas")||t.contiene("cas")||!t.contiene("casa")||
    t.contiene("cama"))throw Error("Trie");
console.log("Prefijos comprobados");

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\trie\MainTrie.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainTrie.js"
 * Salida esperada:
 *   Prefijos comprobados
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
