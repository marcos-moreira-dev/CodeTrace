// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {ListaDoble}=require("./ListaDoble");
/**
 * Cliente Linked List doble: quitar el último nodo en O(1).
 *
 * Los nodos conservan ambos enlaces, a diferencia de Linked List
 * simple. JavaScript administra memoria automáticamente; aun así
 * una referencia externa a un nodo podría impedir su recolección.
 */
function main(){
    const l=new ListaDoble();
    ["A","B","C"].forEach(x=>l.agregarFinal(x));
    if(l.valores().join(",")!=="A,B,C"||l.valores(true).join(",")!=="C,B,A")
        throw Error("Enlaces");
    if(l.quitarFinal()!=="C"||l.cantidad!==2)throw Error("Tail");
    console.log(l.valores());
}
if(require.main===module)main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\linked-list\doble\main.js
 * Salida esperada:
 *   [ 'A', 'B' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
