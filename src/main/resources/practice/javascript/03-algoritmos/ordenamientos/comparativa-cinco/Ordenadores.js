// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check Ordenadores.js
// Ejecutar: node main.js (desde esta carpeta)

/**
 * Cinco sorts clasicos in-place sobre Array de numeros.
 * Insertion/Bubble: peor O(n²), mejor O(n), estables, O(1) aux.
 * Merge: O(n log n), estable con <= izquierda, O(n) aux.
 * Heap: O(n log n), O(1) aux, no estable.
 * Quick pivote ultimo: promedio O(n log n), peor O(n²).
 */
function insercion(a){
    for(let i=1;i<a.length;i++){
        const x=a[i];let j=i-1;
        while(j>=0&&a[j]>x){a[j+1]=a[j];j--;}
        a[j+1]=x;
    }
}
function burbuja(a){
    let limite=a.length;
    while(limite>1){
        let ultimo=0;
        for(let j=1;j<limite;j++)if(a[j-1]>a[j]){
            [a[j-1],a[j]]=[a[j],a[j-1]];ultimo=j;
        }
        limite=ultimo;
    }
}
function mezcla(a){
    const tmp=Array(a.length);
    function ordenar(lo,hi){
        if(hi-lo<=1)return;
        const m=lo+Math.floor((hi-lo)/2);
        ordenar(lo,m);ordenar(m,hi);
        let i=lo,j=m;
        for(let k=lo;k<hi;k++)
            tmp[k]=j===hi||(i<m&&a[i]<=a[j])?a[i++]:a[j++];
        for(let k=lo;k<hi;k++)a[k]=tmp[k];
    }
    ordenar(0,a.length);
}
function heap(a){
    function hundir(n,inicio){
        let i=inicio;
        while(true){
            let mayor=i,iz=2*i+1,de=iz+1;
            if(iz<n&&a[iz]>a[mayor])mayor=iz;
            if(de<n&&a[de]>a[mayor])mayor=de;
            if(mayor===i)return;
            [a[i],a[mayor]]=[a[mayor],a[i]];i=mayor;
        }
    }
    for(let i=Math.floor(a.length/2)-1;i>=0;i--)hundir(a.length,i);
    for(let fin=a.length-1;fin>0;fin--){
        [a[0],a[fin]]=[a[fin],a[0]];hundir(fin,0);
    }
}
function rapido(a){
    function ordenar(lo,hi){
        if(lo>=hi)return;
        const p=a[hi];let i=lo;
        for(let j=lo;j<hi;j++)if(a[j]<=p){
            [a[i],a[j]]=[a[j],a[i]];i++;
        }
        [a[i],a[hi]]=[a[hi],a[i]];
        ordenar(lo,i-1);ordenar(i+1,hi);
    }
    ordenar(0,a.length-1);
}
module.exports={insercion,burbuja,mezcla,heap,rapido};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\ordenamientos\comparativa-cinco\main.js
 * Nota: Ordenadores.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
