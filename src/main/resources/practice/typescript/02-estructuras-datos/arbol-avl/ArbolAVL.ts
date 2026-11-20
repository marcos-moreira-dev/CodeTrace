// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Árbol de búsqueda AVL autoequilibrado con rotaciones.
 * Inserción O(log n) garantizada; un BST ordinario puede degenerar.
 */
class Nodo{
    izquierdo:Nodo|null=null;
    derecho:Nodo|null=null;
    altura=1;
    constructor(readonly dato:number){}
}
const h=(n:Nodo|null):number=>n?.altura??0;
function actualizar(n:Nodo):void{n.altura=1+Math.max(h(n.izquierdo),h(n.derecho));}
function derecha(y:Nodo):Nodo{
    const x=y.izquierdo!;
    y.izquierdo=x.derecho;
    x.derecho=y;
    actualizar(y);actualizar(x);
    return x;
}
function izquierda(x:Nodo):Nodo{
    const y=x.derecho!;
    x.derecho=y.izquierdo;
    y.izquierdo=x;
    actualizar(x);actualizar(y);
    return y;
}
export class ArbolAVL{
    private raiz:Nodo|null=null;
    insertar(dato:number):void{
        const agregar=(n:Nodo|null):Nodo=>{
            if(!n)return new Nodo(dato);
            if(dato<n.dato)n.izquierdo=agregar(n.izquierdo);
            else if(dato>n.dato)n.derecho=agregar(n.derecho);
            else return n;
            actualizar(n);
            const balance=h(n.izquierdo)-h(n.derecho);
            if(balance>1){
                if(dato>n.izquierdo!.dato)n.izquierdo=izquierda(n.izquierdo!);
                return derecha(n);
            }
            if(balance< -1){
                if(dato<n.derecho!.dato)n.derecho=derecha(n.derecho!);
                return izquierda(n);
            }
            return n;
        };
        this.raiz=agregar(this.raiz);
    }
    inorden():number[]{
        const r:number[]=[];
        const recorrer=(n:Nodo|null):void=>{
            if(!n)return;
            recorrer(n.izquierdo);r.push(n.dato);recorrer(n.derecho);
        };
        recorrer(this.raiz);
        return r;
    }
    validar():number{
        const validar=(n:Nodo|null):number=>{
            if(!n)return 0;
            const a=validar(n.izquierdo),b=validar(n.derecho);
            if(Math.abs(a-b)>1||n.altura!==1+Math.max(a,b))throw Error("AVL");
            return n.altura;
        };
        return validar(this.raiz);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\arbol-avl\MainArbolAVL.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainArbolAVL.js"
 * Nota: ArbolAVL.ts es un componente; la salida corresponde al cliente MainArbolAVL.ts.
 * Salida esperada:
 *   Altura AVL: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
