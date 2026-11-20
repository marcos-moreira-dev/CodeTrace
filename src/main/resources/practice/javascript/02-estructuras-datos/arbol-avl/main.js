// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {ArbolAVL}=require("./ArbolAVL");
/**
 * Cliente AVL: un BST sin rotaciones se convierte en cadena al
 * insertar números ordenados. AVL mantiene su altura O(log n).
 * El cliente valida orden inorden, altura y balance local.
 */
function main(){
    const t=new ArbolAVL();
    for(let i=1;i<=15;i++)t.insertar(i);
    if(t.inorden().join(",")!==
       Array.from({length:15},(_,i)=>i+1).join(",")||t.validar()>5)
        throw Error("AVL");
    console.log("Altura AVL:",t.validar());
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Altura AVL:
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
