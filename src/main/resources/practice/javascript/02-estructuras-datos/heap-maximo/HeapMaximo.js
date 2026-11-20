// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check HeapMaximo.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/** Max heap binario sobre Array, insertar y extraer O(log n). */
class HeapMaximo{
    constructor(){this.a=[];}
    insertar(x){
        this.a.push(x);
        let i=this.a.length-1;
        while(i>0){
            const p=(i-1)>>1;
            if(this.a[p]>=this.a[i])break;
            [this.a[p],this.a[i]]=[this.a[i],this.a[p]];i=p;
        }
    }
    extraer(){
        if(!this.a.length)throw Error("Heap vacío");
        const max=this.a[0],ultimo=this.a.pop();
        if(this.a.length){
            this.a[0]=ultimo;
            let i=0;
            while(true){
                let m=i,iz=2*i+1,der=iz+1;
                if(iz<this.a.length&&this.a[iz]>this.a[m])m=iz;
                if(der<this.a.length&&this.a[der]>this.a[m])m=der;
                if(m===i)break;
                [this.a[i],this.a[m]]=[this.a[m],this.a[i]];i=m;
            }
        }
        return max;
    }
}
module.exports={HeapMaximo};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\heap-maximo\main.js
 * Nota: HeapMaximo.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Max heap correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
