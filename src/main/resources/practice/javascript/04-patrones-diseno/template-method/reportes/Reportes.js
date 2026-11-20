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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\template-method\reportes\main.js
 * Nota: Reportes.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
