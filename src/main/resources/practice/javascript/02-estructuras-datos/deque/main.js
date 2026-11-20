// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {DequeCurso}=require("./DequeCurso");
/**
 * Cliente Deque: extremos disponibles para FIFO o LIFO.
 *
 * No usar Array.unshift/shift si se exige tiempo O(1) de forma
 * estructural: pueden desplazar índices. El nodo doble permite
 * inserción y extracción constantes sobre cabeza o cola.
 */
function main(){
    const d=new DequeCurso();
    d.insertarPrimero("B");d.insertarPrimero("A");d.insertarUltimo("C");
    const out=[d.quitarPrimero(),d.quitarUltimo(),d.quitarUltimo()];
    if(out.join(",")!=="A,C,B")throw Error("Deque");
    console.log(out);
}
if(require.main===module)main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\deque\main.js
 * Salida esperada:
 *   [ 'A', 'C', 'B' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
