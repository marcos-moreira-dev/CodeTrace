// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check Reportes.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

/**
 * Template Method: ejecutar fija el orden de pasos del algoritmo.
 * JavaScript no tiene final de Java; el contrato es convencional.
 * Las subclases pueden sustituir encabezado y detalle.
 */
class ProcesoReporte{
    ejecutar(titulo,total){
        if(!titulo||total<0)throw Error("Reporte");
        return this.encabezado(titulo)+this.detalle(total)+this.pie();
    }
    encabezado(){throw Error("Paso abstracto");}
    detalle(){throw Error("Paso abstracto");}
    pie(){return "\n";}
}
class ReporteTexto extends ProcesoReporte{
    encabezado(titulo){return "== "+titulo+" ==\n";}
    detalle(total){return "Total: "+total;}
}
class ReporteCsv extends ProcesoReporte{
    encabezado(){return "titulo,total\n";}
    detalle(total){return "ventas,"+total;}
}
module.exports={ProcesoReporte,ReporteTexto,ReporteCsv};
