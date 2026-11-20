// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

const {Archivo,Carpeta}=require("./Elementos");
/**
 * Cliente Composite: carpetas y archivos como elementos con bytes().
 *
 * Se evita preguntar el tipo real, pues ambos implementan el método
 * esperado por el cliente. La recursión cuesta O(n) nodos; el
 * contrato presupone una jerarquía acíclica, no un grafo arbitrario.
 */
function main(){
    const raiz=new Carpeta("proyecto"),src=new Carpeta("src");
    src.agregar(new Archivo("Main.js",120));
    src.agregar(new Archivo("App.js",80));
    raiz.agregar(src);raiz.agregar(new Archivo("README.md",20));
    if(raiz.bytes()!==220)throw Error("Composite");
    console.log("Bytes:",raiz.bytes());
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Bytes: 220
 * Dos archivos de 120 y 80 y un README de 20 suman 220.
 */
