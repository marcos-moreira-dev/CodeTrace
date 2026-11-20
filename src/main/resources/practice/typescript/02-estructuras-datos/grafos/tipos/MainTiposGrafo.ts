// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde copia (PowerShell): tsc --strict --target ES2022 --module commonjs --noEmit '.\02-estructuras-datos\grafos\tipos\MainTiposGrafo.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\02-estructuras-datos\grafos\tipos\MainTiposGrafo.ts'; node "$env:TEMP\ct-ts\MainTiposGrafo.js"

import { Grafo } from "./Grafo";

/**
 * Cliente: tipos de grafos y su naturaleza independiente.
 *
 * Dirigido NO significa ponderado; pueden aparecer grafos
 * dirigidos sin pesos y no dirigidos con pesos. Un árbol
 * no dirigido es conexo y acíclico; un DAG es dirigido
 * y acíclico, pero no tiene por qué ser un árbol.
 */
const dirigido = new Grafo("dirigido", true);
dirigido.conectar("A", "B", 4.5);
console.log("Arco A->B:", dirigido.vecinos("A"));
console.log("Arco B->A:", dirigido.vecinos("B"));

const red = new Grafo("no-dirigido", false);
red.conectar("A", "B");
red.conectar("B", "C");
red.vertice("AISLADO");
console.log("Componentes:", red.componentes());
if (red.componentes() !== 2) throw Error("Conteo incorrecto");

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\grafos\tipos\MainTiposGrafo.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainTiposGrafo.js"
 * Salida esperada:
 *   Arco A->B: Map(1) { 'B' => 4.5 }
 *   Arco B->A: Map(0) {}
 *   Componentes: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
