// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainUnionFind.js"

import { UnionFind } from "./UnionFind";
/**
 * Cliente DSU: probar conectividad transitiva entre componentes.
 *
 * Esta estructura informa si dos nodos pertenecen al mismo
 * conjunto, pero no reconstruye rutas. Kruskal la aprovecha para
 * decidir qué aristas agregar sin formar ciclos.
 */
const d=new UnionFind(5);
if(!d.unir(0,1)||!d.unir(1,2)||d.unir(0,2)||
    !d.conectados(0,2)||d.conectados(0,4)||d.componentes!==3)
    throw Error("DSU");
console.log("Componentes:",d.componentes);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\union-find\MainUnionFind.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainUnionFind.js"
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
