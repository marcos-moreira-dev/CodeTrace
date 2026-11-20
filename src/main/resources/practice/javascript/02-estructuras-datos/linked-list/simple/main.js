// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {ListaSimple}=require("./ListaSimple");
/**
 * Cliente Linked List simple: nodos con referencias en memoria.
 *
 * Una lista enlazada permite inserción al principio O(1) sin mover
 * los demás datos, a diferencia de un arreglo. El acceso al
 * índice i requiere recorrido O(i), sin propiedad de indexación.
 */
function main(){
    const l=new ListaSimple();
    ["A","B","C"].forEach(x=>l.agregarPrimero(x));
    if(l.valores().join(",")!=="C,B,A"||l.cantidad!==3)throw Error("Lista");
    console.log(l.valores());
}
if(require.main===module)main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\linked-list\simple\main.js
 * Salida esperada:
 *   [ 'C', 'B', 'A' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
