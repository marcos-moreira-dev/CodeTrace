// CodeTrace | Referencia: TypeScript 5.9 / Node.js 24 / ES2022
// Compilar en COPIA: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-sorting" *.ts
// Ejecutar: node "$env:TEMP\ct-sorting\MainOrdenamientos.js"

/**
 * Cinco ordenamientos clasicos en TypeScript, equivalentes a Java.
 *
 * Insertion/Bubble: peor O(n²), mejor O(n), estables, O(1) auxiliar.
 * Merge: O(n log n), estable si prioriza izquierda, O(n) auxiliar.
 * Heap: O(n log n), no estable, O(1) auxiliar.
 * Quick pivote final: esperado O(n log n), peor O(n²) y pila O(n).
 * TS valida tipos al compilar; el resultado JS ordena el mismo arreglo.
 */
export function insercion(a:number[]):void{
    for(let i=1;i<a.length;i++){
        const clave=a[i];let j=i-1;
        while(j>=0&&a[j]>clave){a[j+1]=a[j];j--;}
        a[j+1]=clave;
    }
}
export function burbuja(a:number[]):void{
    let limite=a.length;
    while(limite>1){
        let ultimo=0;
        for(let j=1;j<limite;j++){
            if(a[j-1]>a[j]){
                [a[j-1],a[j]]=[a[j],a[j-1]];
                ultimo=j;
            }
        }
        limite=ultimo;
    }
}
export function mezcla(a:number[]):void{
    const aux:number[]=Array(a.length);
    function ordenar(lo:number,hi:number):void{
        if(hi-lo<=1)return;
        const m=lo+Math.floor((hi-lo)/2);
        ordenar(lo,m);ordenar(m,hi);
        let i=lo,j=m;
        for(let k=lo;k<hi;k++){
            if(j===hi||(i<m&&a[i]<=a[j]))aux[k]=a[i++];
            else aux[k]=a[j++];
        }
        for(let k=lo;k<hi;k++)a[k]=aux[k];
    }
    ordenar(0,a.length);
}
export function heap(a:number[]):void{
    function hundir(n:number,inicio:number):void{
        let i=inicio;
        while(true){
            let mayor=i;
            const iz=2*i+1,de=iz+1;
            if(iz<n&&a[iz]>a[mayor])mayor=iz;
            if(de<n&&a[de]>a[mayor])mayor=de;
            if(mayor===i)return;
            [a[i],a[mayor]]=[a[mayor],a[i]];
            i=mayor;
        }
    }
    for(let i=Math.floor(a.length/2)-1;i>=0;i--)hundir(a.length,i);
    for(let fin=a.length-1;fin>0;fin--){
        [a[0],a[fin]]=[a[fin],a[0]];
        hundir(fin,0);
    }
}
export function rapido(a:number[]):void{
    function ordenar(lo:number,hi:number):void{
        if(lo>=hi)return;
        const pivote=a[hi];
        let i=lo;
        for(let j=lo;j<hi;j++){
            if(a[j]<=pivote){[a[i],a[j]]=[a[j],a[i]];i++;}
        }
        [a[i],a[hi]]=[a[hi],a[i]];
        ordenar(lo,i-1);ordenar(i+1,hi);
    }
    ordenar(0,a.length-1);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\03-algoritmos\ordenamientos\comparativa-cinco\MainOrdenamientos.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainOrdenamientos.js"
 * Nota: Ordenadores.ts es un componente; la salida corresponde al cliente MainOrdenamientos.ts.
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
