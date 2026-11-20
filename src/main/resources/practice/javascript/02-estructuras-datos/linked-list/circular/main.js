// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {ListaCircular}=require("./ListaCircular");
/**
 * Cliente lista circular: un anillo requiere una condición de
 * terminación diferente de la referencia null.
 *
 * Rotar la secuencia mueve tail a su siguiente en O(1). La
 * demostración no usa temporizadores ni hilos: es determinista.
 */
function main(){
    const l=new ListaCircular();
    if(l.valores().length!==0)throw Error("Vacía");
    ["Ana","Luis","Mia"].forEach(x=>l.agregar(x));
    l.rotar();
    if(l.valores().join(",")!=="Luis,Mia,Ana")throw Error("Circular");
    console.log(l.valores());
}
if(require.main===module)main();
