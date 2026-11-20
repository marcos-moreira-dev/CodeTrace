// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * DSU/Union Find: raíz representativa, compresión de caminos
 * y unión por tamaño. Operaciones amortizadas O(alfa(n)).
 */
export class UnionFind{
    private padre:number[];
    private tam:number[];
    componentes:number;
    constructor(n:number){
        if(!Number.isInteger(n)||n<=0)throw Error("Cantidad");
        this.padre=Array.from({length:n},(_,i)=>i);
        this.tam=Array(n).fill(1);
        this.componentes=n;
    }
    raiz(i:number):number{
        if(this.padre[i]!==i)this.padre[i]=this.raiz(this.padre[i]);
        return this.padre[i];
    }
    unir(a:number,b:number):boolean{
        let x=this.raiz(a),y=this.raiz(b);
        if(x===y)return false;
        if(this.tam[x]<this.tam[y])[x,y]=[y,x];
        this.padre[y]=x;
        this.tam[x]+=this.tam[y];
        this.componentes--;
        return true;
    }
    conectados(a:number,b:number):boolean{return this.raiz(a)===this.raiz(b);}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\union-find\MainUnionFind.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainUnionFind.js"
 * Nota: UnionFind.ts es un componente; la salida corresponde al cliente MainUnionFind.ts.
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
