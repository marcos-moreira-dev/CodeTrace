// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check UnionFind.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** DSU con compresión de caminos y unión por tamaño O(alfa(n)). */
class UnionFind{
    constructor(n){
        if(!Number.isInteger(n)||n<=0)throw RangeError("n");
        this.padre=Array.from({length:n},(_,i)=>i);
        this.tamanio=Array(n).fill(1);
        this.componentes=n;
    }
    raiz(x){
        if(this.padre[x]!==x)this.padre[x]=this.raiz(this.padre[x]);
        return this.padre[x];
    }
    unir(a,b){
        let x=this.raiz(a),y=this.raiz(b);
        if(x===y)return false;
        if(this.tamanio[x]<this.tamanio[y])[x,y]=[y,x];
        this.padre[y]=x;this.tamanio[x]+=this.tamanio[y];this.componentes--;
        return true;
    }
    conectados(a,b){return this.raiz(a)===this.raiz(b);}
}
module.exports={UnionFind};
