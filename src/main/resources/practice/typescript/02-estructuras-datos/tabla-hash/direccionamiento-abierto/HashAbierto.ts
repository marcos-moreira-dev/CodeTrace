// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: desde el cliente Main... de esta unidad

/**
 * Hash con sondeo lineal: una colisión busca otra celda.
 * Esperado O(1) con ocupación baja, peor O(n). Sin borrados:
 * requerirían tombstones para no interrumpir las búsquedas.
 */
type Entrada={clave:string;valor:number};
export class HashAbierto {
    private a:(Entrada|undefined)[];
    cantidad=0;
    constructor(capacidad:number){
        if(!Number.isInteger(capacidad)||capacidad<1)throw Error("Capacidad");
        this.a=Array(capacidad);
    }
    private indice(clave:string):number{
        let h=0;
        for(let i=0;i<clave.length;i++)h=(Math.imul(h,31)+clave.charCodeAt(i))|0;
        return (h>>>0)%this.a.length;
    }
    poner(clave:string,valor:number):void{
        const ini=this.indice(clave);
        for(let paso=0;paso<this.a.length;paso++){
            const i=(ini+paso)%this.a.length;
            const anterior=this.a[i];
            if(!anterior){this.a[i]={clave,valor};this.cantidad++;return;}
            if(anterior.clave===clave){this.a[i]={clave,valor};return;}
        }
        throw Error("Tabla llena");
    }
    buscar(clave:string):number|undefined{
        const ini=this.indice(clave);
        for(let paso=0;paso<this.a.length;paso++){
            const anterior=this.a[(ini+paso)%this.a.length];
            if(!anterior)return undefined;
            if(anterior.clave===clave)return anterior.valor;
        }
        return undefined;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\MainHashAbierto.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainHashAbierto.js"
 * Nota: HashAbierto.ts es un componente; la salida corresponde al cliente MainHashAbierto.ts.
 * Salida esperada:
 *   Sondeo comprobado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
