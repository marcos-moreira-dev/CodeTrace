// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Queue FIFO con nodos, cabeza/cola y operaciones O(1). */
class Nodo<T> {
    siguiente:Nodo<T>|null=null;
    constructor(readonly valor:T){}
}
export class ColaEnlazada<T> {
    private primero:Nodo<T>|null=null;
    private ultimo:Nodo<T>|null=null;
    cantidad=0;
    encolar(valor:T):void {
        const n=new Nodo(valor);
        if(this.ultimo)this.ultimo.siguiente=n;
        else this.primero=n;
        this.ultimo=n;this.cantidad++;
    }
    desencolar():T {
        if(!this.primero)throw Error("Cola vacía");
        const dato=this.primero.valor;
        this.primero=this.primero.siguiente;
        if(!this.primero)this.ultimo=null;
        this.cantidad--;
        return dato;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\cola-enlazada\MainColaEnlazada.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainColaEnlazada.js"
 * Nota: ColaEnlazada.ts es un componente; la salida corresponde al cliente MainColaEnlazada.ts.
 * Salida esperada:
 *   Cola enlazada verificada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
