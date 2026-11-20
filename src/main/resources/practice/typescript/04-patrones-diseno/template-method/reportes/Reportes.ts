// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Template Method: secuencia fija + pasos virtuales por herencia.
 *
 * TypeScript no soporta final para métodos como Java. Este ejemplo
 * expresa la convención en la clase base, no una garantía del runtime.
 */
export abstract class ProcesoReporte {
    ejecutar(titulo:string,total:number):string{
        if(!titulo||total<0)throw Error("Reporte");
        return this.encabezado(titulo)+this.detalle(total)+this.pie();
    }
    protected abstract encabezado(titulo:string):string;
    protected abstract detalle(total:number):string;
    protected pie():string{return "\n";}
}
export class ReporteTexto extends ProcesoReporte {
    protected encabezado(t:string):string{return "== "+t+" ==\n";}
    protected detalle(n:number):string{return "Total: "+n;}
}
export class ReporteCsv extends ProcesoReporte {
    protected encabezado(_t:string):string{return "titulo,total\n";}
    protected detalle(n:number):string{return "ventas,"+n;}
}
