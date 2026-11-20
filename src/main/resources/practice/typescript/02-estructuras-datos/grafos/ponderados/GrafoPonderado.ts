// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Grafo dirigido con pesos y listas de adyacencia, memoria O(V+E). */
export interface Arista{destino:string;peso:number;}
export class GrafoPonderado{
    private ady=new Map<string,Arista[]>();
    conectar(origen:string,destino:string,peso:number):void{
        if(!Number.isFinite(peso))throw Error("Peso");
        if(!this.ady.has(origen))this.ady.set(origen,[]);
        if(!this.ady.has(destino))this.ady.set(destino,[]);
        this.ady.get(origen)!.push({destino,peso});
    }
    vecinos(origen:string):ReadonlyArray<Arista>{
        const l=this.ady.get(origen);
        if(!l)throw Error("Origen desconocido");
        return l.map(e=>({...e}));
    }
}
