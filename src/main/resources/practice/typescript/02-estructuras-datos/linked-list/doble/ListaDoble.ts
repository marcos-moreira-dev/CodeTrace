// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Lista doblemente enlazada: dos referencias por nodo.
 *
 * Encolar en ambos extremos O(1), quitar el último O(1),
 * recorrer O(n); espacio O(n). La lista NO permite acceso
 * aleatorio O(1), a diferencia de un arreglo.
 */
class Nodo<T> {
    anterior: Nodo<T> | null = null;
    siguiente: Nodo<T> | null = null;
    constructor(readonly valor: T) {}
}
export class ListaDoble<T> {
    private primero: Nodo<T> | null = null;
    private ultimo: Nodo<T> | null = null;
    cantidad=0;

    agregarFinal(valor: T): void {
        const n=new Nodo(valor);
        n.anterior=this.ultimo;
        if(this.ultimo) this.ultimo.siguiente=n;
        else this.primero=n;
        this.ultimo=n;this.cantidad++;
    }
    quitarFinal(): T {
        if(!this.ultimo) throw Error("Lista vacía");
        const dato=this.ultimo.valor;
        this.ultimo=this.ultimo.anterior;
        if(this.ultimo) this.ultimo.siguiente=null;
        else this.primero=null;
        this.cantidad--;
        return dato;
    }
    adelante(): T[] {
        const r:T[]=[];
        for(let n=this.primero;n!==null;n=n.siguiente)r.push(n.valor);
        return r;
    }
    atras(): T[] {
        const r:T[]=[];
        for(let n=this.ultimo;n!==null;n=n.anterior)r.push(n.valor);
        return r;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\linked-list\doble\MainListaDoble.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainListaDoble.js"
 * Nota: ListaDoble.ts es un componente; la salida corresponde al cliente MainListaDoble.ts.
 * Salida esperada:
 *   [ 'A', 'B' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
