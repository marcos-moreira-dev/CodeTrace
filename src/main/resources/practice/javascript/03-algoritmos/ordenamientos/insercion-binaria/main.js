// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js (desde esta carpeta)

const {ordenar}=require("./InsercionBinaria");
/**
 * Cliente Binary Insertion Sort: encuentra el hueco en O(log n)
 * comparaciones pero sigue moviendo O(n²) elementos de un Array.
 */
function main(){
    const datos=[5,1,3,3,2],vacio=[];
    ordenar(datos);ordenar(vacio);
    if(datos.join(",")!=="1,2,3,3,5"||vacio.length)throw Error("Insercion");
    console.log("Insercion binaria: ["+datos.join(", ")+"]");
}
if(require.main===module)main();
/*
 * Salida esperada:
 * Insercion binaria: [1, 2, 3, 3, 5]
 */
