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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
