// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Deque de lista doble: insertar/quitar por ambos extremos O(1).
 * No se confunde con una cola FIFO: puede servir como pila LIFO.
 */
class Nodo<T>{
    anterior:Nodo<T>|null=null;
    siguiente:Nodo<T>|null=null;
    constructor(readonly valor:T){}
}
export class DequeEnlazado<T>{
    private primero:Nodo<T>|null=null;
    private ultimo:Nodo<T>|null=null;
    insertarPrimero(valor:T):void{
        const n=new Nodo(valor);n.siguiente=this.primero;
        if(this.primero)this.primero.anterior=n;else this.ultimo=n;
        this.primero=n;
    }
    insertarUltimo(valor:T):void{
        const n=new Nodo(valor);n.anterior=this.ultimo;
        if(this.ultimo)this.ultimo.siguiente=n;else this.primero=n;
        this.ultimo=n;
    }
    quitarPrimero():T{
        if(!this.primero)throw Error("Deque vacío");
        const valor=this.primero.valor;
        this.primero=this.primero.siguiente;
        if(this.primero)this.primero.anterior=null;else this.ultimo=null;
        return valor;
    }
    quitarUltimo():T{
        if(!this.ultimo)throw Error("Deque vacío");
        const valor=this.ultimo.valor;
        this.ultimo=this.ultimo.anterior;
        if(this.ultimo)this.ultimo.siguiente=null;else this.primero=null;
        return valor;
    }
}
