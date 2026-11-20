// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check ListaDobleCircular.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Lista doble circular: first.prev=last, last.next=first. */
class Nodo{
    constructor(v){this.valor=v;this.anterior=this;this.siguiente=this;}
}
class ListaDobleCircular{
    constructor(){this.ultimo=null;this.cantidad=0;}
    agregar(valor){
        const n=new Nodo(valor);
        if(this.ultimo){
            const primero=this.ultimo.siguiente;
            n.anterior=this.ultimo;n.siguiente=primero;
            this.ultimo.siguiente=n;primero.anterior=n;
        }
        this.ultimo=n;this.cantidad++;
    }
    valores(inverso=false){
        const r=[];
        if(!this.ultimo)return r;
        let n=inverso?this.ultimo:this.ultimo.siguiente;
        for(let i=0;i<this.cantidad;i++){
            r.push(n.valor);n=inverso?n.anterior:n.siguiente;
        }
        return r;
    }
}
module.exports={ListaDobleCircular};
