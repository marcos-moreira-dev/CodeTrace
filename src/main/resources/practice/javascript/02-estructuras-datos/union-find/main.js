// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {UnionFind}=require("./UnionFind");
/**
 * Cliente DSU: componentes conexas e idempotencia de uniones.
 *
 * Union-Find permite a Kruskal decidir si una arista crearía ciclo.
 * No informa cuál es el camino más corto entre vértices.
 * La compresión y unión por tamaño reducen la altura interna.
 */
function main(){
    const d=new UnionFind(5);
    if(!d.unir(0,1)||!d.unir(1,2)||d.unir(0,2)||
       !d.conectados(0,2)||d.conectados(0,4)||d.componentes!==3)
        throw Error("DSU");
    console.log("Componentes:",d.componentes);
}
if(require.main===module)main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\union-find\main.js
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
