// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainGrafoPonderado.js"

import { GrafoPonderado } from "./GrafoPonderado";
/**
 * Cliente grafo ponderado: modelar aristas ANTES de decidir algoritmo.
 *
 * Un arco A->B no implica B->A; Dijkstra no acepta pesos negativos.
 * La estructura es O(V+E) y permite comparar caminos mínimos
 * sin mezclar representación con el algoritmo.
 */
const g=new GrafoPonderado();
g.conectar("GYE","UIO",12);g.conectar("GYE","CUE",18);g.conectar("UIO","CUE",8);
if(g.vecinos("GYE").length!==2||g.vecinos("CUE").length!==0)
    throw Error("Dirigido");
console.log(g.vecinos("GYE"));
