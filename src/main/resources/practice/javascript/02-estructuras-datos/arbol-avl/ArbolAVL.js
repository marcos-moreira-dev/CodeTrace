// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check ArbolAVL.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/**
 * AVL: BST equilibrado con rotaciones, factor de balance [-1,+1].
 * O(log n) inserción, incluso para entradas ascendentemente ordenadas.
 */
class Nodo{
    constructor(v){this.valor=v;this.izq=null;this.der=null;this.altura=1;}
}
const altura=n=>n?.altura??0;
const actualizar=n=>{n.altura=1+Math.max(altura(n.izq),altura(n.der));};
function derecha(y){
    const x=y.izq;y.izq=x.der;x.der=y;
    actualizar(y);actualizar(x);return x;
}
function izquierda(x){
    const y=x.der;x.der=y.izq;y.izq=x;
    actualizar(x);actualizar(y);return y;
}
class ArbolAVL{
    constructor(){this.raiz=null;}
    insertar(valor){
        function poner(n){
            if(!n)return new Nodo(valor);
            if(valor<n.valor)n.izq=poner(n.izq);
            else if(valor>n.valor)n.der=poner(n.der);
            else return n;
            actualizar(n);
            const b=altura(n.izq)-altura(n.der);
            if(b>1){
                if(valor>n.izq.valor)n.izq=izquierda(n.izq);
                return derecha(n);
            }
            if(b< -1){
                if(valor<n.der.valor)n.der=derecha(n.der);
                return izquierda(n);
            }
            return n;
        }
        this.raiz=poner(this.raiz);
    }
    inorden(){
        const r=[];
        function visitar(n){if(!n)return;visitar(n.izq);r.push(n.valor);visitar(n.der);}
        visitar(this.raiz);return r;
    }
    validar(){
        function recorrer(n){
            if(!n)return 0;
            const a=recorrer(n.izq),b=recorrer(n.der);
            if(Math.abs(a-b)>1||n.altura!==1+Math.max(a,b))throw Error("AVL");
            return n.altura;
        }
        return recorrer(this.raiz);
    }
}
module.exports={ArbolAVL};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\arbol-avl\main.js
 * Nota: ArbolAVL.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Altura AVL: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
