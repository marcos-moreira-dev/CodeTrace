// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Lista circular simple: tail.next enlaza con head; no hay null terminal. */
class Nodo<T> {
    siguiente: Nodo<T>;
    constructor(readonly valor:T){this.siguiente=this;}
}
export class ListaCircular<T> {
    private ultimo:Nodo<T>|null=null;
    private cantidad=0;
    agregar(valor:T):void {
        const n=new Nodo(valor);
        if(this.ultimo) {
            n.siguiente=this.ultimo.siguiente;
            this.ultimo.siguiente=n;
        }
        this.ultimo=n;
        this.cantidad++;
    }
    rotar():void {if(this.ultimo)this.ultimo=this.ultimo.siguiente;}
    valores():T[] {
        const resultado:T[]=[];
        if(!this.ultimo)return resultado;
        let actual=this.ultimo.siguiente;
        for(let i=0;i<this.cantidad;i++){resultado.push(actual.valor);actual=actual.siguiente;}
        return resultado;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\linked-list\circular\MainListaCircular.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainListaCircular.js"
 * Nota: ListaCircular.ts es un componente; la salida corresponde al cliente MainListaCircular.ts.
 * Salida esperada:
 *   [ 'Luis', 'Mia', 'Ana' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
