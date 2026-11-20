// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check ListaDoble.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/**
 * Linked List DOBLE: referencia anterior y siguiente por nodo.
 * Agregar/quitar del final O(1); recorrer ambos sentidos O(n).
 */
class Nodo{
    constructor(valor){this.valor=valor;this.anterior=null;this.siguiente=null;}
}
class ListaDoble{
    constructor(){this.primero=null;this.ultimo=null;this.cantidad=0;}
    agregarFinal(valor){
        const n=new Nodo(valor);
        n.anterior=this.ultimo;
        if(this.ultimo)this.ultimo.siguiente=n;else this.primero=n;
        this.ultimo=n;this.cantidad++;
    }
    quitarFinal(){
        if(!this.ultimo)throw Error("Lista vacía");
        const valor=this.ultimo.valor;
        this.ultimo=this.ultimo.anterior;
        if(this.ultimo)this.ultimo.siguiente=null;else this.primero=null;
        this.cantidad--;return valor;
    }
    valores(inverso=false){
        const r=[];
        for(let n=inverso?this.ultimo:this.primero;n!==null;
            n=inverso?n.anterior:n.siguiente)r.push(n.valor);
        return r;
    }
}
module.exports={ListaDoble};
