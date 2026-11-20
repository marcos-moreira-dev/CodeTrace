// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {ListaDobleCircular}=require("./ListaDobleCircular");
/**
 * Cliente doble circular, combinación de dos invariantes:
 * cada nodo tiene anterior/siguiente y la lista es un anillo.
 *
 * Comparar topología de enlaces con las listas lineales; en
 * JavaScript el GC administra memoria pero los ciclos lógicos
 * continúan requiriendo un límite de recorrido.
 */
function main(){
    const l=new ListaDobleCircular();
    [2,3,5].forEach(x=>l.agregar(x));
    if(l.valores().join(",")!=="2,3,5"||
       l.valores(true).join(",")!=="5,3,2")throw Error("Doble circular");
    console.log(l.valores());
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
