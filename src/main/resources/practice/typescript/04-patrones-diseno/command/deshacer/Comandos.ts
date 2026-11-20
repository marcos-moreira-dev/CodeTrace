// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Command encapsula una operación reversible.
 * Editor es el receptor, no la UI que dispara la petición.
 */
export interface Comando{ejecutar():void;deshacer():void;}
export class Editor{
    texto="";
    agregar(p:string):void{this.texto+=p;}
}
export class AgregarTexto implements Comando{
    private anterior:string|undefined;
    constructor(private readonly editor:Editor,private readonly parte:string){}
    ejecutar():void{
        if(this.anterior!==undefined)throw Error("Ya ejecutado");
        this.anterior=this.editor.texto;
        this.editor.agregar(this.parte);
    }
    deshacer():void{
        if(this.anterior===undefined)throw Error("Sin ejecución");
        this.editor.texto=this.anterior;
        this.anterior=undefined;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\command\deshacer\MainCommand.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainCommand.js"
 * Nota: Comandos.ts es un componente; la salida corresponde al cliente MainCommand.ts.
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
