// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Composite: una hoja y una carpeta ofrecen bytes().
 * El cálculo es recursivo O(n nodos), no lectura real del disco.
 */
export interface Elemento{bytes():number;}
export class Archivo implements Elemento{
    constructor(readonly nombre:string,readonly tamanio:number){
        if(tamanio<0)throw Error("Tamaño");
    }
    bytes():number{return this.tamanio;}
}
export class Carpeta implements Elemento{
    private hijos:Elemento[]=[];
    constructor(readonly nombre:string){}
    agregar(e:Elemento):void{
        if(e===this)throw Error("Ciclo directo");
        this.hijos.push(e);
    }
    bytes():number{return this.hijos.reduce((n,e)=>n+e.bytes(),0);}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\composite\arbol-archivos\MainComposite.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainComposite.js"
 * Nota: Elementos.ts es un componente; la salida corresponde al cliente MainComposite.ts.
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
