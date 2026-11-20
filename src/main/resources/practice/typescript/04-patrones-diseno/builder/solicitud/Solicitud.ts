// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Producto validado con propiedades readonly. */
export interface Solicitud{
    readonly ruta:string;
    readonly metodo:string;
    readonly cabeceras:ReadonlyArray<string>;
}
/** Builder fluido, almacena estado mutable hasta construir el producto. */
export class SolicitudBuilder{
    private _ruta="";
    private _metodo="GET";
    private _cabeceras:string[]=[];
    ruta(x:string):this{this._ruta=x;return this;}
    metodo(x:string):this{this._metodo=x;return this;}
    cabecera(x:string):this{this._cabeceras.push(x);return this;}
    construir():Solicitud{
        if(!this._ruta.startsWith("/")||!this._metodo)throw Error("Solicitud");
        // readonly del tipo no congela objetos JS: Object.freeze es la protección runtime.
        return Object.freeze({
            ruta:this._ruta,metodo:this._metodo,
            cabeceras:Object.freeze([...this._cabeceras])
        });
    }
}
