// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {GrafoPonderado}=require("./GrafoPonderado");
/**
 * Cliente grafo ponderado: representación distinta de algoritmo.
 *
 * Las aristas A->B son dirigidas, no se agregan inversas de forma
 * implícita. Dijkstra necesita pesos no negativos; el modelo
 * admite otras variantes, pero no selecciona una ruta.
 */
function main(){
    const g=new GrafoPonderado();
    g.conectar("GYE","UIO",12);g.conectar("GYE","CUE",18);
    g.conectar("UIO","CUE",8);
    if(g.vecinos("GYE").length!==2||g.vecinos("CUE").length!==0)
        throw Error("Grafo");
    console.log(g.vecinos("GYE"));
}
if(require.main===module)main();
