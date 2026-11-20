// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/** Max heap binario sobre arreglo: padre>=hijos, O(log n) inserción/extracción. */
export class HeapMaximo {
    private a:number[]=[];
    insertar(valor:number):void{
        this.a.push(valor);
        let i=this.a.length-1;
        while(i>0){
            const p=(i-1)>>1;
            if(this.a[p]>=this.a[i])break;
            [this.a[p],this.a[i]]=[this.a[i],this.a[p]];
            i=p;
        }
    }
    extraer():number{
        if(!this.a.length)throw Error("Heap vacío");
        const max=this.a[0],ultimo=this.a.pop()!;
        if(this.a.length){
            this.a[0]=ultimo;
            let i=0;
            while(true){
                let m=i;
                const iz=2*i+1,der=iz+1;
                if(iz<this.a.length&&this.a[iz]>this.a[m])m=iz;
                if(der<this.a.length&&this.a[der]>this.a[m])m=der;
                if(m===i)break;
                [this.a[i],this.a[m]]=[this.a[m],this.a[i]];
                i=m;
            }
        }
        return max;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\heap-maximo\MainHeapMaximo.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainHeapMaximo.js"
 * Nota: HeapMaximo.ts es un componente; la salida corresponde al cliente MainHeapMaximo.ts.
 * Salida esperada:
 *   Heap máximo OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
