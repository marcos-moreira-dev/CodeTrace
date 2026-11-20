// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check DequeCurso.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Deque manual por nodos dobles: cuatro operaciones O(1). */
class Nodo{
    constructor(v){this.valor=v;this.anterior=null;this.siguiente=null;}
}
class DequeCurso{
    constructor(){this.primero=null;this.ultimo=null;}
    insertarPrimero(v){
        const n=new Nodo(v);n.siguiente=this.primero;
        if(this.primero)this.primero.anterior=n;else this.ultimo=n;
        this.primero=n;
    }
    insertarUltimo(v){
        const n=new Nodo(v);n.anterior=this.ultimo;
        if(this.ultimo)this.ultimo.siguiente=n;else this.primero=n;
        this.ultimo=n;
    }
    quitarPrimero(){
        if(!this.primero)throw Error("Deque vacío");
        const dato=this.primero.valor;
        this.primero=this.primero.siguiente;
        if(this.primero)this.primero.anterior=null;else this.ultimo=null;
        return dato;
    }
    quitarUltimo(){
        if(!this.ultimo)throw Error("Deque vacío");
        const dato=this.ultimo.valor;
        this.ultimo=this.ultimo.anterior;
        if(this.ultimo)this.ultimo.siguiente=null;else this.primero=null;
        return dato;
    }
}
module.exports={DequeCurso};
