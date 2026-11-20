// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Lista doble circular: head.prev=tail y tail.next=head. */
class Nodo<T> {
    anterior:Nodo<T>;
    siguiente:Nodo<T>;
    constructor(readonly valor:T){this.anterior=this;this.siguiente=this;}
}
export class ListaDobleCircular<T> {
    private ultimo:Nodo<T>|null=null;
    private cantidad=0;
    agregar(valor:T):void {
        const n=new Nodo(valor);
        if(this.ultimo) {
            const primero=this.ultimo.siguiente;
            n.anterior=this.ultimo;
            n.siguiente=primero;
            this.ultimo.siguiente=n;
            primero.anterior=n;
        }
        this.ultimo=n;this.cantidad++;
    }
    valores(inverso=false):T[] {
        const salida:T[]=[];
        if(!this.ultimo)return salida;
        let actual=inverso?this.ultimo:this.ultimo.siguiente;
        for(let i=0;i<this.cantidad;i++){
            salida.push(actual.valor);
            actual=inverso?actual.anterior:actual.siguiente;
        }
        return salida;
    }
}
