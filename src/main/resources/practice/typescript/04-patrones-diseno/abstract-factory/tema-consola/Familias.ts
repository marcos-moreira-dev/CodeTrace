// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Abstract Factory: Boton y Dialogo de una misma familia visual. */
export interface Boton{pintar():string;}
export interface Dialogo{pintar():string;}
export interface Fabrica{boton():Boton;dialogo():Dialogo;}
class Texto implements Boton,Dialogo{
    constructor(private readonly contenido:string){}
    pintar():string{return this.contenido;}
}
export class Claro implements Fabrica{
    boton():Boton{return new Texto("[ OK ]");}
    dialogo():Dialogo{return new Texto("Ventana clara");}
}
export class Oscuro implements Fabrica{
    boton():Boton{return new Texto("< OK >");}
    dialogo():Dialogo{return new Texto("Ventana oscura");}
}
