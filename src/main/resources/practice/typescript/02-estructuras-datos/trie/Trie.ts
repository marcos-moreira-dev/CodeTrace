// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Trie: árbol de prefijos, consulta O(L) promedio para L caracteres. */
class Nodo {
    hijos=new Map<string,Nodo>();
    terminal=false;
}
export class Trie{
    private raiz=new Nodo();
    insertar(palabra:string):void{
        let actual=this.raiz;
        for(const c of palabra){
            let hijo=actual.hijos.get(c);
            if(!hijo){hijo=new Nodo();actual.hijos.set(c,hijo);}
            actual=hijo;
        }
        actual.terminal=true;
    }
    private nodo(texto:string):Nodo|undefined{
        let actual=this.raiz;
        for(const c of texto){
            const sig=actual.hijos.get(c);
            if(!sig)return undefined;
            actual=sig;
        }
        return actual;
    }
    contiene(texto:string):boolean{return this.nodo(texto)?.terminal??false;}
    prefijo(texto:string):boolean{return this.nodo(texto)!==undefined;}
}
