// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

const {ReporteTexto,ReporteCsv}=require("./Reportes");
/**
 * Cliente Template Method: secuencia estable por herencia.
 *
 * Contrastar con Strategy funcional/closures: Strategy recibe
 * comportamiento como argumento; Template Method permite que
 * subclases modifiquen pasos del mismo algoritmo base.
 */
function main(){
    const a=new ReporteTexto(),b=new ReporteCsv();
    if(!a.ejecutar("ventas",15).includes("Total: 15")||
       !b.ejecutar("ventas",15).includes("ventas,15"))
        throw Error("Template Method");
    console.log(a.ejecutar("ventas",15)+b.ejecutar("ventas",15));
}
if(require.main===module)main();
